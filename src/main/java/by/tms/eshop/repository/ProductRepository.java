package by.tms.eshop.repository;

import by.tms.eshop.domain.Product;
import by.tms.eshop.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Set;

public interface ProductRepository {

    Page<ProductDto> getProductsByCategory(String category, Pageable pageable);

    String getProductCategoryValue(Long id);

    Set<Product> getFoundedProducts(String searchCondition);

    Product getProduct(Long id);

    Set<Product> selectAllProductsByFilter(String type, BigDecimal minPrice, BigDecimal maxPrice);
}