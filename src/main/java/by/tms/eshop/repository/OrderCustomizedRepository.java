package by.tms.eshop.repository;

import by.tms.eshop.domain.Product;
import java.util.List;

public interface OrderCustomizedRepository {

    Long createOrder(String order, Long id);

    void saveProductInOrderConfigurations(Long id, List<Product> products);
}
