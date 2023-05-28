package by.tms.eshop.repository;

import by.tms.eshop.domain.Product;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductCustomizedRepository {

    Page<Product> findAllWithPaginationByProductCategory_Category(String category, Pageable pageable);

    @Query("SELECT productCategory.category FROM Product WHERE id = :id")
    String getProductCategoryValue(Long id);

    Set<Product> getFoundedProducts(String searchCondition);

    Optional<Product> findById(Long id);

    Set<Product> selectAllProductsByFilter(String type, BigDecimal minPrice, BigDecimal maxPrice);
}