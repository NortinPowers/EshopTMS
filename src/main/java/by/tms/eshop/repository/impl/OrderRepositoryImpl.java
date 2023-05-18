package by.tms.eshop.repository.impl;

import by.tms.eshop.domain.Order;
import by.tms.eshop.domain.Product;
import by.tms.eshop.repository.OrderCustomizedRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static by.tms.eshop.utils.RepositoryJdbcUtils.getOrder;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderCustomizedRepository {
//public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    private final EntityManager entityManager;
//    private final SessionFactory sessionFactory;

//    private static final String GET_ORDERS_BY_ID = "FROM Order WHERE user.id = :userId";
//    private static final String CHECK_ORDERS_NAME = "FROM Order WHERE name = :name";

    @Override
    public Long createOrder(String name, Long id) {
//        Session session = entityManager.getCurrentSession();
        Order order = getOrder(name, id);
//        session.persist(order);
        entityManager.persist(order);
        return order.getId();
    }

    @Override
    public void saveProductInOrderConfigurations(Long id, List<Product> products) {
//        Session session = entityManager.getCurrentSession();
        Order order = entityManager.find(Order.class, id);
//        Order order = session.get(Order.class, id);
        order.setProducts(products);
        products.forEach(product -> product.setOrders(new ArrayList<>(List.of(order))));
    }

//    @Override
//    public List<Order> getOrdersById(Long id) {
//        Session session = sessionFactory.getCurrentSession();
//        return session.createQuery(GET_ORDERS_BY_ID, Order.class)
//                .setParameter(USER_ID, id)
//                .getResultList();
//    }
//
//    @Override
//    public boolean checkOrderNumber(String name) {
//        Session session = sessionFactory.getCurrentSession();
//        return session.createQuery(CHECK_ORDERS_NAME, Order.class)
//                .setParameter(NAME, name)
//                .getResultList().stream()
//                .anyMatch(order -> order.getName().equals(name));
//    }
}