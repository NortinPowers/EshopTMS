package by.tms.eshop.repository;

import by.tms.eshop.domain.Product;
import java.math.BigDecimal;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @SuppressWarnings("checkstyle:MethodName")
    Page<Product> findAllWithPaginationByProductCategory_Category(String category, Pageable pageable);

    @Query("SELECT productCategory.category FROM Product WHERE id = :id")
    String getProductCategoryValue(Long id);

    @Query("FROM Product WHERE price >= :minPrice AND price <= :maxPrice AND productCategory.category = :category ORDER BY id")
    Set<Product> selectProductsFromCategoryByFilter(String category, BigDecimal minPrice, BigDecimal maxPrice);

    @Query("FROM Product WHERE price >= :minPrice AND price <= :maxPrice  ORDER BY id")
    Set<Product> selectAllProductsByFilter(BigDecimal minPrice, BigDecimal maxPrice);

    @Query("FROM Product WHERE LOWER (name) LIKE LOWER ('%' || :condition || '%')")
    Set<Product> getProductsByConditionInName(String condition);

    @Query("FROM Product WHERE LOWER (info) LIKE LOWER ('%' || :condition || '%')")
    Set<Product> getProductsByConditionInInfo(String condition);
}
