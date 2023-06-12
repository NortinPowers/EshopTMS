package by.tms.eshop.service.impl;

import static by.tms.eshop.utils.ControllerUtils.createOrderNumber;

import by.tms.eshop.domain.Order;
import by.tms.eshop.domain.Product;
import by.tms.eshop.domain.User;
import by.tms.eshop.dto.OrderDto;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.dto.conversion.Converter;
import by.tms.eshop.repository.OrderRepository;
import by.tms.eshop.service.OrderService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final Converter converter;

    @Override
    public Long createOrder(Long id) {
        String orderNumber = generateOrderNumber(id);
        Order order = Order.builder()
                           .name(orderNumber)
                           .date(LocalDate.now())
                           .user(User.builder()
                                     .id(id)
                                     .build())
                           .build();
        orderRepository.save(order);
        return order.getId();
    }

    @Override
    public void saveProductInOrderConfigurations(Long id, List<Product> products) {
        Order order = orderRepository.findOrderById(id);
        order.setProducts(products);
    }

    @Override
    public List<OrderDto> getOrdersById(Long id) {
        List<Order> orderById = orderRepository.findOrderByUserId(id);
        converter.getOrdersDtosFromOrders(orderById);
        return converter.getOrdersDtosFromOrders(orderRepository.findOrderByUserId(id));
    }

    @Override
    public boolean checkOrderNumber(String number) {
        return orderRepository.existsByName(number);
    }

    @Override
    public void saveUserOrder(Long userId, List<ProductDto> productsDto) {
        Long order = createOrder(userId);
        List<Product> products = converter.getProductsFromProductsDtos(productsDto);
        saveProductInOrderConfigurations(order, products);
    }

    private String generateOrderNumber(Long id) {
        String orderNumber = "";
        while (checkOrderNumber(orderNumber) || StringUtils.isEmpty(orderNumber)) {
            orderNumber = createOrderNumber(id);
        }
        return orderNumber;
    }
}
