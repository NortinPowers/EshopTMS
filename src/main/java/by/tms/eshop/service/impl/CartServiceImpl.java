package by.tms.eshop.service.impl;

import by.tms.eshop.domain.Cart;
import by.tms.eshop.domain.Product;
import by.tms.eshop.domain.User;
import by.tms.eshop.dto.CartDto;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.mapper.CartMapper;
import by.tms.eshop.mapper.ProductMapper;
import by.tms.eshop.model.Location;
import by.tms.eshop.repository.CartRepository;
import by.tms.eshop.service.CartService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductMapper productMapper;
    private final CartMapper cartMapper;

//    @Transactional
//    @Override
//    public void addSelectedProduct(Long userId, Long productId, LocationDto locationDto) {
//        cartRepository.addSelectedProduct(userId, productId, locationDto);
//    }

//    @Transactional
//    @Override
//    public void addSelectedProduct(Long userId, Long productId, Location location) {
//        cartRepository.addSelectedProduct(userId, productId, location);
//    }

//    @Override
//    public List<ImmutablePair<ProductDto, Integer>> getSelectedProducts(Long userId, LocationDto locationDto) {
//        return cartRepository.getSelectedProducts(userId, locationDto);
//    }

//    @Override
//    public List<ImmutablePair<ProductDto, Integer>> getSelectedProducts(Long userId, Location location) {
//        return cartRepository.getSelectedProducts(userId, location);
//    }

//    @Override
//    public List<ImmutablePair<ProductDto, Integer>> getSelectedProducts(Long userId, Location location) {
//        if (location.isCart()) {
////            List<ImmutablePair<Product, Integer>> cartProducts = cartRepository.getCartProducts(userId);
////            return cartRepository.getCartProducts(userId).stream()
////                                                                             .map(pair -> ImmutablePair.of(productMapper.convertToProductDto(pair.getLeft()), pair.getRight()))
////                                                                             .collect(Collectors.toList());
////            for (ImmutablePair<Product, Integer> cartProduct : cartProducts) {
////                productMapper.convertToProductDto(cartProduct.getLeft());
////            }
////            return cartRepository.getCartProducts(userId)
//            return getImmutablePairsProductDtoCount(cartRepository.getCartProducts(userId));
//        } else {
//            return getImmutablePairsProductDtoCount(cartRepository.getFavoriteProducts(userId));
////            return cartRepository.getFavoriteProducts(userId);
//        }
////        return cartRepository.getSelectedProducts(userId, location);
//    }

    @Override
    public List<CartDto> getSelectedProducts(Long userId, Location location) {
        if (location.isCart()) {
//            return getImmutablePairsProductDtoCount(cartRepository.getCartProducts(userId));
            return convertToCartDtos(cartRepository.getCartProducts(userId));
        } else {
            return convertToCartDtos(cartRepository.getFavoriteProducts(userId));
//            return cartRepository.getFavoriteProducts(userId);
        }
//        return cartRepository.getSelectedProducts(userId, location);
    }

//    @Transactional
//    @Override
//    public void deleteProduct(Long userId, Long productId, LocationDto locationDto) {
//        cartRepository.deleteProduct(userId, productId, locationDto);
//    }

    //    @Transactional
//    @Override
//    public void deleteProduct(Long userId, Long productId, Location location) {
//        cartRepository.deleteProduct(userId, productId, location);
//    }
    @Transactional
    @Override
    public void deleteCartProductsAfterBuy(Long userId) {
//        cartRepository.deleteCartByUserId(userId);
        cartRepository.deleteCartByUserIdAndCart(userId, true);
    }

//    @Override
//    public List<ProductDto> getPurchasedProducts(Long userId, LocationDto locationDto) {
//        return cartRepository.getPurchasedProducts(userId, locationDto);
//    }
    //    @Override
//    public List<ProductDto> getPurchasedProducts(Long userId, Location location) {
//        return cartRepository.getPurchasedProducts(userId, location);
//    }

//    @Override
//    public List<ProductDto> getPurchasedProducts(Long userId, Location location) {
//        List<ProductDto> products = new ArrayList<>();
////        List<ImmutablePair<ProductDto, Integer>> productWithCount = getSelectedProducts(userId, location);
////        List<ImmutablePair<ProductDto, Integer>> productWithCount = cartRepository.getCartProducts(userId);
//        List<ImmutablePair<ProductDto, Integer>> productWithCount = getImmutablePairsProductDtoCount(cartRepository.getCartProducts(userId));
//        for (Pair<ProductDto, Integer> productIntegerPair : productWithCount) {
//            Integer count = productIntegerPair.getRight();
//            while (count > 0) {
//                products.add(productIntegerPair.getLeft());
//                count--;
//            }
//        }
//        return products;
//    }

    @Override
    public List<ProductDto> getPurchasedProducts(Long userId, Location location) {
//        List<ProductDto> products = new ArrayList<>();
//        List<ImmutablePair<ProductDto, Integer>> productWithCount = getSelectedProducts(userId, location);
//        List<ImmutablePair<ProductDto, Integer>> productWithCount = cartRepository.getCartProducts(userId);
//        List<ImmutablePair<ProductDto, Integer>> productWithCount = getImmutablePairsProductDtoCount(cartRepository.getCartProducts(userId));
        List<ProductDto> productDtos = new ArrayList<>();
        List<CartDto> carts = convertToCartDtos(cartRepository.getCartProducts(userId));
        for (CartDto cart : carts) {
            Integer count = cart.getCount();
            while (count > 0) {
                productDtos.add(cart.getProductDto());
                count--;
            }
        }
//        for (Pair<ProductDto, Integer> productIntegerPair : productWithCount) {
//            Integer count = productIntegerPair.getRight();
//            while (count > 0) {
//                products.add(productIntegerPair.getLeft());
//                count--;
//            }
//        }
        return productDtos;
    }

    @Transactional
    @Override
    public void addSelectedProduct(Long userId, Long productId, Location location) {
        if (location.isFavorite()) {
            Optional<Cart> userFavorite = cartRepository.getUserFavorite(userId, productId);
            if (userFavorite.isEmpty()) {
                addProduct(userId, productId, location);
            }
//            if (checkProduct(userId, productId, location)) {
//                addProduct(userId, productId, location);
//            }
        } else {
            Optional<Cart> userCart = cartRepository.getUserCart(userId, productId);
            if (userCart.isEmpty()) {
                addProduct(userId, productId, location);
            } else {
                Cart cart = userCart.get();
                Integer count = userCart.get().getCount();
                cart.setCount(++count);
            }
//            if (checkProduct(userId, productId, location)) {
//                addProduct(userId, productId, location);
//            } else {
//                modifyProductCount(userId, productId, true);
//            }
        }
    }

    @Transactional
    @Override
    public void deleteProduct(Long userId, Long productId, Location location) {
//        cartRepository.deleteProduct(userId, productId, location);
//    }
//
//    @Override
//    public void deleteProduct(Long userId, Long productId, Location location) {
        if (location.isFavorite()) {
            Optional<Cart> userFavorite = cartRepository.getUserFavorite(userId, productId);
            userFavorite.ifPresent(cartRepository::delete);
//            deleteProductByMark(userId, productId, GET_CURRENT_FAVORITE);
        } else {
            Optional<Cart> userCart = cartRepository.getUserCart(userId, productId);
            if (userCart.isPresent()) {
                Cart cart = userCart.get();
                Integer count = cart.getCount();
                if (count > 1) {
                    cart.setCount(--count);
                } else {
                    cartRepository.delete(cart);
                }
            }
////            Integer productCount = getCartProductCount(userId, productId);
//            Integer productCount = cart.getCount();
//            if (productCount > 1) {
//                modifyProductCount(userId, productId, false);
//            } else {
////                deleteProductByMark(userId, productId, GET_CURRENT_CART);
//                Cart userCartProduct = cartRepository.getUserCart(userId, productId);
//                cartRepository.delete(userCartProduct);
//            }
        }
    }

    private List<CartDto> convertToCartDtos(List<Cart> carts) {
        return carts.stream()
                    .map(cartMapper::convertToCartDto)
                    .toList();
    }

    private void addProduct(Long userId, Long productId, Location location) {
        Cart cart = getCart(userId, productId, location);
        cartRepository.save(cart);
//        entityManager.persist(cart);
    }

//    public boolean checkProduct(Long userId, Long productId, Location location) {
//        List<ProductDto> productsDto = getProducts(userId, location);
//        return isProductNotIncluded(productId, productsDto);
//    }

//    private List<ProductDto> getProducts(Long userId, Location location) {
//        return getSelectedProducts(userId, location).stream()
//                                                    .map(Pair::getLeft)
//                                                    .collect(Collectors.toList());
//    }

    //    private boolean isProductNotIncluded(Long productId, List<ProductDto> products) {
//        return products.stream()
//                       .filter(product -> Objects.equals(product.getId(), productId))
//                       .findAny()
//                       .isEmpty();
//    }
    private Cart getCart(Long userId, Long productId, Location location) {
        return Cart.builder()
                   .user(User.builder()
                             .id(userId)
                             .build())
                   .product(Product.builder()
                                   .id(productId)
                                   .build())
                   .favorite(location.isFavorite())
//                   .favorite(locationDto.isFavorite())
                   .cart(location.isCart())
//                   .cart(locationDto.isCart())
                   .count(1)
                   .build();
    }

//    private void modifyProductCount(Long userId, Long productId, boolean up) {
//        Integer productCount = getCartProductCount(userId, productId);
//        productCount = getModifyCount(up, productCount);
//        Cart cart = cartRepository.getUserCart(userId, productId);
//        cart.setCount(productCount);
//        cartRepository.save(cart);
////        entityManager.merge(cart);
//    }

//    @SuppressWarnings("checkstyle:ParameterAssignment")
//    private Integer getModifyCount(boolean up, Integer productCount) {
//        return up ? ++productCount : --productCount;
//    }

//    private Cart getCurrentCart(Long userId, Long productId, String query) {
//        return entityManager.createQuery(query, Cart.class)
//                            .setParameter(USER_ID, userId)
//                            .setParameter(PRODUCT_ID, productId)
//                            .getSingleResult();
//    }

    //    public Integer getCartProductCount(Long userId, Long productId) {
//        List<Cart> carts = entityManager.createQuery(GET_CURRENT_CART, Cart.class)
//                                        .setParameter(USER_ID, userId)
//                                        .setParameter(PRODUCT_ID, productId)
//                                        .getResultList();
//        return carts.stream()
//                    .map(Cart::getCount)
//                    .findAny()
//                    .orElse(0);
//    }
//    private List<ImmutablePair<ProductDto, Integer>> getProductDtoIntegerPair(List<ImmutablePair<Product, Integer>> productIntegerPair) {
//        return productIntegerPair.stream()
//                                 .map(pair -> ImmutablePair.of(productMapper.convertToProductDto(pair.getLeft()), pair.getRight()))
//                                 .collect(Collectors.toList());
//    }

//    private List<ImmutablePair<ProductDto, Integer>> getImmutablePairsProductDtoCount(List<Cart> carts) {
//        List<ImmutablePair<ProductDto, Integer>> pairs = new ArrayList<>();
//        carts.forEach(cart -> pairs.add(new ImmutablePair<>(productMapper.convertToProductDto(cart.getProduct()), cart.getCount())));
////        carts.forEach(cart -> pairs.add(new ImmutablePair<>(ProductDto.builder()
////                                                                      .id(cart.getProduct().getId())
////                                                                      .name(cart.getProduct().getName())
////                                                                      .category(cart.getProduct().getProductCategory().getCategory())
////                                                                      .price(cart.getProduct().getPrice())
////                                                                      .info(cart.getProduct().getInfo())
////                                                                      .build(), cart.getCount())));
//        return pairs;
//    }
}
