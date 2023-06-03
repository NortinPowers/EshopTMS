package by.tms.eshop.repository;

import by.tms.eshop.domain.Order;
import by.tms.eshop.domain.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderCustomizedRepository {

    Long createOrder(String order, Long id);

    void saveProductInOrderConfigurations(Long id, List<Product> products);

    List<Order> findOrderByUserId(Long id);

    boolean existsByName(String number);
}
