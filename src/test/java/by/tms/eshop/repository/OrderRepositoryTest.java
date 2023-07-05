package by.tms.eshop.repository;

import static by.tms.eshop.test_utils.Constants.MapperConstants.USER_ID;
import static by.tms.eshop.test_utils.Constants.TEST_PROPERTY_SOURCE_LOCATIONS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import by.tms.eshop.domain.Order;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = TEST_PROPERTY_SOURCE_LOCATIONS)
@Sql(value = "classpath:sql/order/order-repository-before.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:sql/order/order-repository-after.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Nested
    class TestFindOrderByUserId {

        private Long userId = USER_ID;

        @Test
        void test_findOrderByUserId_isPresent() {
            int expectedSize = 1;

            List<Order> orders = orderRepository.findOrderByUserId(userId);

            assertFalse(orders.isEmpty());
            assertEquals(expectedSize, orders.size());
        }

        @Test
        void test_findOrderByUserId_isNotPresent() {
            userId = 0L;

            List<Order> orders = orderRepository.findOrderByUserId(userId);

            assertTrue(orders.isEmpty());
        }
    }

    @Nested
    class TestExistsByName {

        private String orderNumber = "#1";

        @Test
        void test_existsByName_isPresent() {
            boolean exist = orderRepository.existsByName(orderNumber);

            assertTrue(exist);
        }

        @Test
        void test_existsByName_isNotPresent() {
            orderNumber = "#0";

            boolean exist = orderRepository.existsByName(orderNumber);

            assertFalse(exist);
        }
    }

    @Nested
    class TestFindOrderById {

        private Long orderId = 1L;

        @Test
        void test_findOrderById_isPresent() {
            Long userId = 1L;

            Order order = orderRepository.findOrderById(orderId);

            assertNotNull(order);
            assertEquals(userId, order.getUser().getId());
        }

        @Test
        void test_findOrderById_isNotPresent() {
            orderId = 0L;

            Order order = orderRepository.findOrderById(orderId);

            assertNull(order);
        }
    }
}
