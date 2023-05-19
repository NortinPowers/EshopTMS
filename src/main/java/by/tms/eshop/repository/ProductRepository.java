package by.tms.eshop.repository;

import by.tms.eshop.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductCustomizedRepository {

//    Page<ProductDto> getProductsByCategory(String category, Pageable pageable);
//    List<Product> findAllByProductCategory_Category(String category);
    Page<Product> findAllWithPaginationByProductCategory_Category(String category, Pageable pageable);

    @Query("SELECT productCategory.category FROM Product WHERE id = :id")
    //select pc.category from products on p join product_category on pc where id = ?
    String getProductCategoryValue(Long id);
//    String findByProductCategory_Id(Long id);

    Set<Product> getFoundedProducts(String searchCondition);

//    Product getProduct(Long id);
    Optional<Product> findById(Long id);

    Set<Product> selectAllProductsByFilter(String type, BigDecimal minPrice, BigDecimal maxPrice);
}