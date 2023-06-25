package by.tms.eshop.repository;

import by.tms.eshop.domain.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrderByUserId(Long id);

    boolean existsByName(String number);

    Order findOrderById(Long id);
}
