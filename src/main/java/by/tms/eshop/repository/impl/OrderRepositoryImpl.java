package by.tms.eshop.repository.impl;

import by.tms.eshop.domain.Order;
import by.tms.eshop.domain.Product;
import by.tms.eshop.domain.User;
import by.tms.eshop.repository.OrderCustomizedRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderCustomizedRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Long createOrder(String name, Long id) {
        Order order = getOrder(name, id);
        entityManager.persist(order);
        return order.getId();
    }

    @Override
    public void saveProductInOrderConfigurations(Long id, List<Product> products) {
        Order order = entityManager.find(Order.class, id);
        order.setProducts(products);
        products.forEach(product -> product.setOrders(List.of(order)));
    }

    private Order getOrder(String order, Long id) {
        return Order.builder()
                    .name(order)
                    .date(LocalDate.now())
                    .user(User.builder()
                              .id(id)
                              .build())
                    .build();
    }
}