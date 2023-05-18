package by.tms.eshop.repository;

import by.tms.eshop.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

//    List<String> getProductCategory();
    @Query("SELECT category FROM ProductCategory")
    List<String> findAllCategory();
}