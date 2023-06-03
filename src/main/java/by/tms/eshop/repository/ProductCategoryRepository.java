package by.tms.eshop.repository;

import by.tms.eshop.domain.ProductCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query("SELECT category FROM ProductCategory")
    List<String> findAllCategory();
}
