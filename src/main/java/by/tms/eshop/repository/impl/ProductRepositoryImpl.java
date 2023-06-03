package by.tms.eshop.repository.impl;

import static by.tms.eshop.utils.Constants.ALL;
import static by.tms.eshop.utils.Constants.QueryParameter.CONDITION;
import static by.tms.eshop.utils.Constants.QueryParameter.MAX_PRICE;
import static by.tms.eshop.utils.Constants.QueryParameter.MIN_PRICE;

import by.tms.eshop.domain.Product;
import by.tms.eshop.repository.ProductCustomizedRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductCustomizedRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private static final String GET_PRODUCTS_BY_SEARCH_CONDITION_IN_NAME = "FROM Product WHERE LOWER (name) LIKE LOWER ('%' || :condition || '%')";
    private static final String GET_PRODUCTS_BY_SEARCH_CONDITION_IN_INFO = "FROM Product WHERE LOWER (info) LIKE LOWER ('%' || :condition || '%')";
    private static final String SELECT_ALL_PRODUCTS_BY_FILTER = "FROM Product WHERE price >= :minPrice AND price <= :maxPrice";

    @Override
    public Set<Product> getFoundedProducts(String condition) {
        Set<Product> products = new LinkedHashSet<>(getSearchProductsByCondition(condition, GET_PRODUCTS_BY_SEARCH_CONDITION_IN_NAME, entityManager));
        products.addAll(getSearchProductsByCondition(condition, GET_PRODUCTS_BY_SEARCH_CONDITION_IN_INFO, entityManager));
        return products;
    }

    @Override
    public Set<Product> selectAllProductsByFilter(String category, BigDecimal minPrice, BigDecimal maxPrice) {
        String query = SELECT_ALL_PRODUCTS_BY_FILTER;
        if (!ALL.equals(category)) {
            query += " AND productCategory.category = '" + category + "' ORDER BY id";
        } else {
            query += " ORDER BY id";
        }
        return new LinkedHashSet<>(getSearchProductsByPrice(minPrice, maxPrice, query, entityManager));
    }

    private List<Product> getSearchProductsByCondition(String condition, String query, EntityManager entityManager) {
        return entityManager.createQuery(query, Product.class)
                            .setParameter(CONDITION, condition)
                            .getResultList();
    }

    private List<Product> getSearchProductsByPrice(BigDecimal minPrice, BigDecimal maxPrice, String query, EntityManager entityManager) {
        return entityManager.createQuery(query, Product.class)
                            .setParameter(MIN_PRICE, minPrice)
                            .setParameter(MAX_PRICE, maxPrice)
                            .getResultList();
    }
}
