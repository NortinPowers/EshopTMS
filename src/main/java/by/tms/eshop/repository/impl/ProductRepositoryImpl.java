package by.tms.eshop.repository.impl;

import by.tms.eshop.domain.Product;
import by.tms.eshop.repository.ProductCustomizedRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import static by.tms.eshop.utils.RepositoryJdbcUtils.getQueryDependType;
import static by.tms.eshop.utils.RepositoryJdbcUtils.getSearchProductsByCondition;
import static by.tms.eshop.utils.RepositoryJdbcUtils.getSearchProductsByPrice;

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
        String query = getQueryDependType(category, SELECT_ALL_PRODUCTS_BY_FILTER);
        return new LinkedHashSet<>(getSearchProductsByPrice(minPrice, maxPrice, query, entityManager));
    }
}