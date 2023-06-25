package by.tms.eshop.service.impl;

import by.tms.eshop.domain.Cart;
import by.tms.eshop.domain.Product;
import by.tms.eshop.domain.User;
import by.tms.eshop.dto.CartDto;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.mapper.CartMapper;
import by.tms.eshop.model.Location;
import by.tms.eshop.repository.CartRepository;
import by.tms.eshop.service.CartService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    @Override
    public List<CartDto> getSelectedProducts(Long userId, Location location) {
        if (location.isCart()) {
            return convertToCartDtos(cartRepository.getCartProducts(userId));
        } else {
            return convertToCartDtos(cartRepository.getFavoriteProducts(userId));
        }
    }

    @Transactional
    @Override
    public void deleteCartProductsAfterBuy(Long userId) {
        cartRepository.deleteCartByUserIdAndCart(userId, true);
    }

    @Override
    public List<ProductDto> getPurchasedProducts(Long userId, Location location) {
        List<ProductDto> productDtos = new ArrayList<>();
        List<CartDto> carts = convertToCartDtos(cartRepository.getCartProducts(userId));
        for (CartDto cart : carts) {
            Integer count = cart.getCount();
            while (count > 0) {
                productDtos.add(cart.getProductDto());
                count--;
            }
        }
        return productDtos;
    }

    @Override
    public List<Map<Long, Long>> getMostFavorite() {
        return cartRepository.getMostFavorite();
    }

    @Transactional
    @Override
    public void addSelectedProduct(Long userId, Long productId, Location location) {
        if (location.isFavorite()) {
            Optional<Cart> userFavorite = cartRepository.getUserFavorite(userId, productId);
            if (userFavorite.isEmpty()) {
                addProduct(userId, productId, location);
            }
        } else {
            Optional<Cart> userCart = cartRepository.getUserCart(userId, productId);
            if (userCart.isEmpty()) {
                addProduct(userId, productId, location);
            } else {
                Cart cart = userCart.get();
                Integer count = cart.getCount();
                cart.setCount(++count);
            }
        }
    }

    @Transactional
    @Override
    public void deleteProduct(Long userId, Long productId, Location location) {
        if (location.isFavorite()) {
            Optional<Cart> userFavorite = cartRepository.getUserFavorite(userId, productId);
            userFavorite.ifPresent(cartRepository::delete);
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
    }

    private Cart getCart(Long userId, Long productId, Location location) {
        return Cart.builder()
                   .user(User.builder()
                             .id(userId)
                             .build())
                   .product(Product.builder()
                                   .id(productId)
                                   .build())
                   .favorite(location.isFavorite())
                   .cart(location.isCart())
                   .count(1)
                   .build();
    }
}
