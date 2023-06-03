package by.tms.eshop.service;

import by.tms.eshop.domain.Product;
import by.tms.eshop.dto.OrderDto;
import by.tms.eshop.dto.ProductDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {

    Long createOrder(Long id);

    void saveProductInOrderConfigurations(Long id, List<Product> products);

    List<OrderDto> getOrdersById(Long id);

    boolean checkOrderNumber(String number);

    @Transactional
    void saveUserOrder(Long userId, List<ProductDto> productsDto);
}
