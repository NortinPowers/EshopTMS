package by.tms.eshop.repository;

import static by.tms.eshop.test_utils.Constants.MapperConstants.USER_ID;
import static by.tms.eshop.test_utils.Constants.TEST_PROPERTY_SOURCE_LOCATIONS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import by.tms.eshop.domain.Cart;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = TEST_PROPERTY_SOURCE_LOCATIONS)
@Sql(value = "classpath:sql/cart/cart-repository-before.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:sql/cart/cart-repository-after.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    private Long userId = USER_ID;
    private Long productId;
    private Integer count;
    private Integer expectedSize;

    @Test
    void test_getMostFavorite() {
        expectedSize = 2;

        List<Map<Long, Long>> mostFavorite = cartRepository.getMostFavorite();

        assertFalse(mostFavorite.isEmpty());
        assertEquals(expectedSize, mostFavorite.size());
    }

    @Nested
    class TestGetUserFavorite {

        @Test
        void test_getUserFavorite_isPresent() {
            productId = 2L;
            count = 1;

            Optional<Cart> cart = cartRepository.getUserFavorite(userId, productId);

            assertTrue(cart.isPresent());
            assertEquals(count, cart.get().getCount());

        }

        @Test
        void test_getUserFavorite_isNotPresent() {
            userId = 0L;

            Optional<Cart> cart = cartRepository.getUserFavorite(userId, productId);

            assertFalse(cart.isPresent());
        }

    }

    @Nested
    class TestGetUserCart {

        @Test
        void test_getUserCart_isPresent() {
            productId = 1L;
            count = 2;

            Optional<Cart> cart = cartRepository.getUserCart(userId, productId);

            assertTrue(cart.isPresent());
            assertEquals(count, cart.get().getCount());

        }

        @Test
        void test_getUserCart_isNotPresent() {
            userId = 0L;

            Optional<Cart> cart = cartRepository.getUserCart(userId, productId);

            assertFalse(cart.isPresent());
        }

    }

    @Nested
    class TestGetFavoriteProducts {

        @Test
        void test_getFavoriteProducts_isPresent() {
            expectedSize = 2;

            List<Cart> favoriteProducts = cartRepository.getFavoriteProducts(userId);

            assertFalse(favoriteProducts.isEmpty());
            assertEquals(expectedSize, favoriteProducts.size());
        }

        @Test
        void test_getFavoriteProducts_isNotPresent() {
            userId = 0L;

            List<Cart> favoriteProducts = cartRepository.getFavoriteProducts(userId);

            assertTrue(favoriteProducts.isEmpty());
        }
    }

    @Nested
    class TestGetCartProducts {

        @Test
        void test_getCartProducts_isPresent() {
            expectedSize = 1;

            List<Cart> cartProducts = cartRepository.getCartProducts(userId);

            assertFalse(cartProducts.isEmpty());
            assertEquals(expectedSize, cartProducts.size());
        }

        @Test
        void test_getCartProducts_isNotPresent() {
            userId = 0L;

            List<Cart> cartProducts = cartRepository.getCartProducts(userId);

            assertTrue(cartProducts.isEmpty());
        }
    }

    @Test
    @Transactional
    void test_deleteCartByUserIdAndCart() {
        cartRepository.deleteCartByUserIdAndCart(userId, true);
        List<Cart> cartProducts = cartRepository.getCartProducts(userId);

        assertTrue(cartProducts.isEmpty());
    }
}
