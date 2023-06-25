package by.tms.eshop.service;

import by.tms.eshop.domain.Product;
import by.tms.eshop.dto.OrderDto;
import by.tms.eshop.dto.ProductDto;
import java.util.List;

public interface OrderService {

    Long createOrder(Long id);

    void saveProductInOrderConfigurations(Long id, List<Product> products);

    List<OrderDto> getOrdersById(Long id);

    boolean checkOrderNumber(String number);

    void saveUserOrder(Long userId, List<ProductDto> productsDto);
}
