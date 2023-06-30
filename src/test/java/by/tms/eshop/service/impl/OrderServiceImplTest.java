package by.tms.eshop.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.tms.eshop.domain.Order;
import by.tms.eshop.domain.Product;
import by.tms.eshop.dto.OrderDto;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.mapper.OrderMapper;
import by.tms.eshop.mapper.ProductMapper;
import by.tms.eshop.repository.OrderRepository;
import by.tms.eshop.service.OrderService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private OrderMapper orderMapper;

    @MockBean
    private ProductMapper productMapper;

    private final Order order = Order.builder().build();
    private final Long userId = 1L;
    private Long orderId = 1L;

    @Test
    void test_createOrder() {
        String name = "some";
        order.setName(name);
        order.setId(3L);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        orderId = orderService.createOrder(userId);

        assertEquals(order.getId(), orderId);
        verify(orderRepository, atLeastOnce()).save(any(Order.class));
    }

    @Test
    void test_saveProductInOrderConfigurations() {
        List<Product> products = List.of(Product.builder().build());

        when(orderRepository.findOrderById(orderId)).thenReturn(order);

        orderService.saveProductInOrderConfigurations(orderId, products);

        assertEquals(products, order.getProducts());
        verify(orderRepository, atLeastOnce()).findOrderById(orderId);
    }

    @Test
    void test_getOrdersById() {
        List<Order> orders = List.of(order);
        OrderDto orderDto = OrderDto.builder().build();
        List<OrderDto> orderDtos = List.of(orderDto);

        when(orderRepository.findOrderByUserId(userId)).thenReturn(orders);
        when(orderMapper.convertToOrderDto(order)).thenReturn(orderDto);

        List<OrderDto> foundOrders = orderService.getOrdersById(orderId);

        assertEquals(orderDtos, foundOrders);
        verify(orderRepository, atLeastOnce()).findOrderByUserId(userId);
    }

    @Test
    void test_checkOrderNumber() {
        boolean flag = true;
        String number = "someNumber";

        when(orderRepository.existsByName(number)).thenReturn(flag);

        boolean check = orderService.checkOrderNumber(number);

        assertEquals(flag, check);
        verify(orderRepository, atLeastOnce()).existsByName(number);
    }

    @Test
    void test_saveUserOrder() {
        Product product = Product.builder().build();
        List<Product> products = List.of(product);
        ProductDto productDto = ProductDto.builder().build();
        List<ProductDto> productDtos = List.of(productDto);
        order.setId(orderId);

        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderRepository.findOrderById(orderId)).thenReturn(order);
        when(productMapper.convertToProduct(productDto)).thenReturn(product);

        orderService.saveUserOrder(userId, productDtos);

        assertEquals(products, order.getProducts());
        verify(orderRepository, atLeastOnce()).save(any(Order.class));
        verify(orderRepository, atLeastOnce()).findOrderById(orderId);
    }
}
