package by.tms.eshop.repository.impl;

import static by.tms.eshop.utils.Constants.QueryParameter.PRODUCT_ID;
import static by.tms.eshop.utils.Constants.QueryParameter.USER_ID;

import by.tms.eshop.domain.Cart;
import by.tms.eshop.domain.Product;
import by.tms.eshop.domain.User;
import by.tms.eshop.dto.LocationDto;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.repository.CartCustomizedRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartCustomizedRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private static final String GET_CART_PRODUCTS_BY_USER_ID = "FROM Cart WHERE user.id = :userId AND cart = true";
    private static final String GET_FAVORITE_PRODUCTS_BY_USER_ID = "FROM Cart WHERE user.id = :userId AND favorite = true";
    private static final String GET_CURRENT_CART = "FROM Cart WHERE user.id = :userId AND product.id = :productId AND cart = true";
    private static final String GET_CURRENT_FAVORITE = "FROM Cart WHERE user.id = :userId AND product.id = :productId AND favorite = true";

    @Override
    public void addSelectedProduct(Long userId, Long productId, LocationDto locationDto) {
        if (locationDto.isFavorite()) {
            if (checkProduct(userId, productId, locationDto)) {
                addProduct(userId, productId, locationDto);
            }
        } else {
            if (checkProduct(userId, productId, locationDto)) {
                addProduct(userId, productId, locationDto);
            } else {
                modifyProductCount(userId, productId, true);
            }
        }
    }

    @Override
    public void deleteProduct(Long userId, Long productId, LocationDto locationDto) {
        if (locationDto.isFavorite()) {
            deleteProductByMark(userId, productId, GET_CURRENT_FAVORITE);
        } else {
            Integer productCount = getCartProductCount(userId, productId);
            if (productCount > 1) {
                modifyProductCount(userId, productId, false);
            } else {
                deleteProductByMark(userId, productId, GET_CURRENT_CART);
            }
        }
    }

    @Override
    public List<ImmutablePair<ProductDto, Integer>> getSelectedProducts(Long userId, LocationDto locationDto) {
        String query = locationDto.isCart() ? GET_CART_PRODUCTS_BY_USER_ID : GET_FAVORITE_PRODUCTS_BY_USER_ID;
        List<Cart> carts = entityManager.createQuery(query, Cart.class)
                .setParameter(USER_ID, userId)
                .getResultList();
        return getImmutablePairsProductDtoCount(carts);
    }

    @Override
    public boolean checkProduct(Long userId, Long productId, LocationDto locationDto) {
        List<ProductDto> productsDto = getProducts(userId, locationDto);
        return isProductNotIncluded(productId, productsDto);
    }

    @Override
    public Integer getCartProductCount(Long userId, Long productId) {
        List<Cart> carts = entityManager.createQuery(GET_CURRENT_CART, Cart.class)
                                        .setParameter(USER_ID, userId)
                                        .setParameter(PRODUCT_ID, productId)
                                        .getResultList();
        return carts.stream()
                .map(Cart::getCount)
                .findAny()
                .orElse(0);
    }

    @Override
    public List<ProductDto> getPurchasedProducts(Long userId, LocationDto locationDto) {
        List<ProductDto> products = new ArrayList<>();
        List<ImmutablePair<ProductDto, Integer>> productWithCount = getSelectedProducts(userId, locationDto);
        for (Pair<ProductDto, Integer> productIntegerPair : productWithCount) {
            Integer count = productIntegerPair.getRight();
            while (count > 0) {
                products.add(productIntegerPair.getLeft());
                count--;
            }
        }
        return products;
    }

    private void addProduct(Long userId, Long productId, LocationDto locationDto) {
        Cart cart = getCart(userId, productId, locationDto);
        entityManager.persist(cart);
    }

    private void modifyProductCount(Long userId, Long productId, boolean up) {
        Integer productCount = getCartProductCount(userId, productId);
        productCount = getModifyCount(up, productCount);
        Cart cart = getCurrentCart(userId, productId, GET_CURRENT_CART, entityManager);
        cart.setCount(productCount);
        entityManager.merge(cart);
    }

    private List<ProductDto> getProducts(Long userId, LocationDto locationDto) {
        return getSelectedProducts(userId, locationDto).stream()
                .map(Pair::getLeft)
                .collect(Collectors.toList());
    }

    private void deleteProductByMark(Long userId, Long productId, String query) {
        Cart cart = getCurrentCart(userId, productId, query, entityManager);
        entityManager.remove(cart);
    }

    private boolean isProductNotIncluded(Long productId, List<ProductDto> products) {
        return products.stream()
                       .filter(product -> Objects.equals(product.getId(), productId))
                       .findAny()
                       .isEmpty();
    }

    private Integer getModifyCount(boolean up, Integer productCount) {
        return up ? ++productCount : --productCount;
    }

    private Cart getCart(Long userId, Long productId, LocationDto locationDto) {
        return Cart.builder()
                   .user(User.builder()
                             .id(userId)
                             .build())
                   .product(Product.builder()
                                   .id(productId)
                                   .build())
                   .favorite(locationDto.isFavorite())
                   .cart(locationDto.isCart())
                   .count(1)
                   .build();
    }

    private Cart getCurrentCart(Long userId, Long productId, String query, EntityManager entityManager) {
        return entityManager.createQuery(query, Cart.class)
                            .setParameter(USER_ID, userId)
                            .setParameter(PRODUCT_ID, productId)
                            .getSingleResult();
    }

    private List<ImmutablePair<ProductDto, Integer>> getImmutablePairsProductDtoCount(List<Cart> carts) {
        List<ImmutablePair<ProductDto, Integer>> pairs = new ArrayList<>();
        carts.forEach(cart -> pairs.add(new ImmutablePair<>(ProductDto.builder()
                                                                      .id(cart.getProduct().getId())
                                                                      .name(cart.getProduct().getName())
                                                                      .category(cart.getProduct().getProductCategory().getCategory())
                                                                      .price(cart.getProduct().getPrice())
                                                                      .info(cart.getProduct().getInfo())
                                                                      .build(), cart.getCount())));
        return pairs;
    }
}