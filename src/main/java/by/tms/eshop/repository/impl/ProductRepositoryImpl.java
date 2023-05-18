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
//public abstract class ProductRepositoryImpl implements ProductRepository {

    @PersistenceContext
    private final EntityManager entityManager;
//    private final SessionFactory sessionFactory;

//    private static final String GET_PRODUCTS_BY_CATEGORY = "FROM Product WHERE productCategory.category = :category";
//    private static final String GET_PRODUCT_CATEGORY = "SELECT productCategory.category FROM Product WHERE id = :id";
    private static final String GET_PRODUCTS_BY_SEARCH_CONDITION_IN_NAME = "FROM Product WHERE LOWER (name) LIKE LOWER ('%' || :condition || '%')";
    private static final String GET_PRODUCTS_BY_SEARCH_CONDITION_IN_INFO = "FROM Product WHERE LOWER (info) LIKE LOWER ('%' || :condition || '%')";
    private static final String SELECT_ALL_PRODUCTS_BY_FILTER = "FROM Product WHERE price >= :minPrice AND price <= :maxPrice";

//    @Override
//    public Page<ProductDto> getProductsByCategory(String category, Pageable pageable) {
//        Session session = sessionFactory.getCurrentSession();
//        List<ProductDto> products = session.createQuery(GET_PRODUCTS_BY_CATEGORY, Product.class)
//                .setParameter(CATEGORY, category)
//                .getResultList().stream()
//                .map(DtoUtils::makeProductDtoModelTransfer)
//                .toList();
//        PagedListHolder<ProductDto> pageHolder = getPagedListHolder(pageable, products);
//        return new PageImpl<>(pageHolder.getPageList(), pageable, products.size());
//    }

//    @Override
//    public Product getProduct(Long id) {
//        Session session = sessionFactory.getCurrentSession();
//        return session.get(Product.class, id);
//    }

//    @Override
//    public String getProductCategoryValue(Long id) {
//        Session session = sessionFactory.getCurrentSession();
//        return session.createQuery(GET_PRODUCT_CATEGORY, String.class)
//                .setParameter(ID, id)
//                .getSingleResult();
//    }

    @Override
    public Set<Product> getFoundedProducts(String condition) {
//        Session session = entityManager.getCurrentSession();
        Set<Product> products = new LinkedHashSet<>(getSearchProductsByCondition(condition, GET_PRODUCTS_BY_SEARCH_CONDITION_IN_NAME, entityManager));
//        Set<Product> products = new LinkedHashSet<>(getSearchProductsByCondition(condition, GET_PRODUCTS_BY_SEARCH_CONDITION_IN_NAME, session));
        products.addAll(getSearchProductsByCondition(condition, GET_PRODUCTS_BY_SEARCH_CONDITION_IN_INFO, entityManager));
//        products.addAll(getSearchProductsByCondition(condition, GET_PRODUCTS_BY_SEARCH_CONDITION_IN_INFO, session));
        return products;
    }

    @Override
    public Set<Product> selectAllProductsByFilter(String category, BigDecimal minPrice, BigDecimal maxPrice) {
        String query = getQueryDependType(category, SELECT_ALL_PRODUCTS_BY_FILTER);
//        Session session = entityManager.getCurrentSession();
        return new LinkedHashSet<>(getSearchProductsByPrice(minPrice, maxPrice, query, entityManager));
//        return new LinkedHashSet<>(getSearchProductsByPrice(minPrice, maxPrice, query, session));
    }
}