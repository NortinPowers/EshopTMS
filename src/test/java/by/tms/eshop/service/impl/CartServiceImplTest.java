package by.tms.eshop.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.tms.eshop.domain.Cart;
import by.tms.eshop.domain.Product;
import by.tms.eshop.dto.CartDto;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.mapper.CartMapper;
import by.tms.eshop.model.Location;
import by.tms.eshop.repository.CartRepository;
import by.tms.eshop.service.CartService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class CartServiceImplTest {

    @Autowired
    private CartService cartService;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private CartMapper cartMapper;

    private final Long userId = 1L;
    private final Long productId = 1L;

    private Location location;

    @Test
    void test_deleteCartProductsAfterBuy() {
        doNothing().when(cartRepository).deleteCartByUserIdAndCart(userId, true);

        cartService.deleteCartProductsAfterBuy(userId);

        verify(cartRepository, atLeastOnce()).deleteCartByUserIdAndCart(userId, true);
    }

    @Test
    void test_getPurchasedProducts() {
        Product productOne = Product.builder().build();
        Product productTwo = Product.builder().build();
        Integer productOneCount = 1;
        Integer productTwoCount = 2;
        Cart cartOne = Cart.builder()
                           .product(productOne)
                           .count(productOneCount)
                           .cart(true)
                           .build();
        Cart cartTwo = Cart.builder()
                           .product(productTwo)
                           .count(productTwoCount)
                           .cart(true)
                           .build();
        List<Cart> carts = List.of(cartOne, cartTwo);
        ProductDto productDtoOne = ProductDto.builder().build();
        ProductDto productDtoTwo = ProductDto.builder().build();
        List<ProductDto> productDtos = List.of(productDtoOne, productDtoTwo, productDtoTwo);
        CartDto cartDtoOne = CartDto.builder()
                                    .productDto(productDtoOne)
                                    .count(productOneCount)
                                    .cart(true)
                                    .build();
        CartDto cartDtoTwo = CartDto.builder()
                                    .productDto(productDtoTwo)
                                    .count(productTwoCount)
                                    .cart(true)
                                    .build();
        List<CartDto> cartDtos = List.of(cartDtoOne, cartDtoTwo);

        when(cartRepository.getCartProducts(userId)).thenReturn(carts);
        when(cartMapper.convertToCartDtos(carts)).thenReturn(cartDtos);

        List<ProductDto> foundProducts = cartService.getPurchasedProducts(userId, Location.CART);

        assertEquals(productDtos, foundProducts);
        verify(cartRepository, atLeastOnce()).getCartProducts(userId);
    }

    @Test
    void test_getMostFavorite() {
        Long productOneId = 1L;
        Long productOneCount = 4L;
        Long productTwoId = 2L;
        Long productTwoCount = 3L;
        List<Map<Long, Long>> maps = List.of(Map.of(productOneId, productOneCount), Map.of(productTwoId, productTwoCount));

        when(cartRepository.getMostFavorite()).thenReturn(maps);

        List<Map<Long, Long>> foundMaps = cartService.getMostFavorite();

        assertEquals(maps, foundMaps);
        verify(cartRepository, atLeastOnce()).getMostFavorite();
    }

    @Nested
    class TestGetSelectedProducts {

        private final List<Cart> carts = List.of(Cart.builder().build());
        private final List<CartDto> cartDtos = List.of(CartDto.builder().build());

        @Test
        void test_getSelectedProducts_locationCart() {
            location = Location.CART;

            when(cartRepository.getCartProducts(userId)).thenReturn(carts);
            when(cartMapper.convertToCartDtos(carts)).thenReturn(cartDtos);

            List<CartDto> foundCarts = cartService.getSelectedProducts(userId, location);

            assertEquals(cartDtos, foundCarts);
            verify(cartRepository, atLeastOnce()).getCartProducts(userId);
        }

        @Test
        void test_getSelectedProducts_locationFavorite() {
            location = Location.FAVORITE;

            when(cartRepository.getFavoriteProducts(userId)).thenReturn(carts);
            when(cartMapper.convertToCartDtos(carts)).thenReturn(cartDtos);

            List<CartDto> foundCarts = cartService.getSelectedProducts(userId, location);

            assertEquals(cartDtos, foundCarts);
            verify(cartRepository, atLeastOnce()).getFavoriteProducts(userId);
        }
    }

    @Nested
    class TestAddSelectedProduct {

        private final Cart cart = Cart.builder()
                                      .count(1)
                                      .build();

        @Test
        void test_addSelectedProduct_locationFavorite_emptyCart() {
            location = Location.FAVORITE;

            when(cartRepository.getUserFavorite(userId, productId)).thenReturn(Optional.empty());
            when(cartRepository.save(any(Cart.class))).thenReturn(cart);

            cartService.addSelectedProduct(userId, productId, location);

            verify(cartRepository, atLeastOnce()).getUserFavorite(userId, productId);
            verify(cartRepository, atLeastOnce()).save(any(Cart.class));
        }

        @Test
        void test_addSelectedProduct_locationFavorite_isPresentCart() {
            location = Location.FAVORITE;

            when(cartRepository.getUserFavorite(userId, productId)).thenReturn(Optional.of(cart));

            cartService.addSelectedProduct(userId, productId, location);

            verify(cartRepository, atLeastOnce()).getUserFavorite(userId, productId);
            verify(cartRepository, never()).save(any(Cart.class));
        }

        @Test
        void test_addSelectedProduct_locationCart_emptyCart() {
            location = Location.CART;

            when(cartRepository.getUserCart(userId, productId)).thenReturn(Optional.empty());
            when(cartRepository.save(any(Cart.class))).thenReturn(cart);

            cartService.addSelectedProduct(userId, productId, location);

            verify(cartRepository, atLeastOnce()).getUserCart(userId, productId);
            verify(cartRepository, atLeastOnce()).save(any(Cart.class));
        }

        @Test
        void test_addSelectedProduct_locationCart_isPresentCart() {
            location = Location.CART;
            Integer count = cart.getCount();
            Integer result = ++count;

            when(cartRepository.getUserCart(userId, productId)).thenReturn(Optional.of(cart));
            when(cartRepository.save(any(Cart.class))).thenReturn(cart);

            cartService.addSelectedProduct(userId, productId, location);

            assertEquals(result, cart.getCount());
            verify(cartRepository, atLeastOnce()).getUserCart(userId, productId);
            verify(cartRepository, never()).save(any(Cart.class));
        }
    }

    @Nested
    class TestDeleteProduct {

        private final Cart cart = Cart.builder().build();

        @Test
        void test_deleteProduct_locationFavorite_isPresentCart() {
            location = Location.FAVORITE;
            cart.setCount(1);

            when(cartRepository.getUserFavorite(userId, productId)).thenReturn(Optional.of(cart));
            doNothing().when(cartRepository).delete(cart);

            cartService.deleteProduct(userId, productId, location);

            verify(cartRepository, atLeastOnce()).getUserFavorite(userId, productId);
            verify(cartRepository, atLeastOnce()).delete(cart);
        }

        @Test
        void test_deleteProduct_locationFavorite_emptyCart() {
            location = Location.FAVORITE;

            when(cartRepository.getUserFavorite(userId, productId)).thenReturn(Optional.empty());

            cartService.deleteProduct(userId, productId, location);

            verify(cartRepository, atLeastOnce()).getUserFavorite(userId, productId);
            verify(cartRepository, never()).delete(cart);
        }

        @Test
        void test_deleteProduct_locationCart_isPresentCart_countGreaterOne() {
            location = Location.CART;
            cart.setCount(2);
            Integer result = cart.getCount() - 1;

            when(cartRepository.getUserCart(userId, productId)).thenReturn(Optional.of(cart));

            cartService.deleteProduct(userId, productId, location);

            assertEquals(result, cart.getCount());
            verify(cartRepository, atLeastOnce()).getUserCart(userId, productId);
            verify(cartRepository, never()).delete(any(Cart.class));
        }

        @Test
        void test_deleteProduct_locationCart_isPresentCart_countOne() {
            location = Location.CART;
            cart.setCount(1);

            when(cartRepository.getUserCart(userId, productId)).thenReturn(Optional.of(cart));

            cartService.deleteProduct(userId, productId, location);

            verify(cartRepository, atLeastOnce()).getUserCart(userId, productId);
            verify(cartRepository, atLeastOnce()).delete(any(Cart.class));
        }

        @Test
        void test_deleteProduct_locationCart_emptyCart() {
            location = Location.CART;

            when(cartRepository.getUserCart(userId, productId)).thenReturn(Optional.empty());

            cartService.deleteProduct(userId, productId, location);

            verify(cartRepository, atLeastOnce()).getUserCart(userId, productId);
            verify(cartRepository, never()).delete(any(Cart.class));
        }
    }
}
