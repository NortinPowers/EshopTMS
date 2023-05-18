package by.tms.eshop.repository.impl;

import by.tms.eshop.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductCategoryRepositoryImpl implements ProductCategoryRepository {

    private  final SessionFactory sessionFactory;

    private static final String GET_CATEGORY = "SELECT category FROM ProductCategory";

    @Override
    public List<String> getProductCategory() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(GET_CATEGORY, String.class).getResultList();
    }
}