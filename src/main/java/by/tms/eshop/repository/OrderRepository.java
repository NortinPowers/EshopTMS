package by.tms.eshop.repository;

import by.tms.eshop.domain.Order;
import by.tms.eshop.domain.Product;

import java.util.List;

public interface OrderRepository {

    Long createOrder(String order, Long id);

    void saveProductInOrderConfigurations(Long id, List<Product> products);

    List<Order> getOrdersById(Long id);

    boolean checkOrderNumber(String number);
}