package by.tms.eshop.service;

import by.tms.eshop.dto.ProductDto;
import java.math.BigDecimal;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.ModelAndView;

public interface ProductService {

    ModelAndView getProductsByCategory(String category, Pageable pageable);

//    ModelAndView getProduct(Long id, String location);
    ModelAndView getProduct(Long id);

    String getProductCategoryValue(Long productId);

    Set<ProductDto> getFoundedProducts(String condition);

    Set<ProductDto> selectAllProductsByFilter(BigDecimal minPrice, BigDecimal maxPrice);

    Set<ProductDto> selectProductsFromCategoryByFilter(String category, BigDecimal minPrice, BigDecimal maxPrice);
}
