package by.tms.eshop.repository;

import by.tms.eshop.domain.Product;

import java.math.BigDecimal;
import java.util.Set;

public interface ProductCustomizedRepository {

    Set<Product> getFoundedProducts(String searchCondition);

    Set<Product> selectAllProductsByFilter(String type, BigDecimal minPrice, BigDecimal maxPrice);
}