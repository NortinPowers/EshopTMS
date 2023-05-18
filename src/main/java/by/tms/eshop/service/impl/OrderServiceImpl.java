package by.tms.eshop.service.impl;

import by.tms.eshop.domain.Product;
import by.tms.eshop.dto.OrderDto;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.repository.OrderRepository;
import by.tms.eshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static by.tms.eshop.utils.ControllerUtils.createOrderNumber;
import static by.tms.eshop.utils.DtoUtils.getOrdersDtosFromOrders;
import static by.tms.eshop.utils.DtoUtils.getProductsFromProductsDtos;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Long createOrder(Long id) {
        String orderNumber = generateOrderNumber(id);
        return orderRepository.createOrder(orderNumber, id);
    }

    @Override
    public void saveProductInOrderConfigurations(Long id, List<Product> products) {
        orderRepository.saveProductInOrderConfigurations(id, products);
    }

    @Override
    public List<OrderDto> getOrdersById(Long id) {
        return getOrdersDtosFromOrders(orderRepository.getOrdersById(id));
    }

    @Override
    public boolean checkOrderNumber(String number) {
        return orderRepository.checkOrderNumber(number);
    }

    @Override
    public void saveUserOrder(Long userId, List<ProductDto> productsDto) {
        Long order = createOrder(userId);
        List<Product> products = getProductsFromProductsDtos(productsDto);
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