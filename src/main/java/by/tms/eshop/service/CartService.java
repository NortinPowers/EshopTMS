package by.tms.eshop.service;

import by.tms.eshop.dto.LocationDto;
import by.tms.eshop.dto.ProductDto;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {

    void addSelectedProduct(Long userId, Long productId, LocationDto locationDto);

    List<ImmutablePair<ProductDto, Integer>> getSelectedProducts(Long userId, LocationDto locationDto);

    void deleteProduct(Long userId, Long productId, LocationDto locationDto);

    void deleteCartProductsAfterBuy(Long userId);

    BigDecimal getProductsPrice(List<ImmutablePair<ProductDto, Integer>> productWithCount);

    List<ProductDto> getPurchasedProducts(Long userId, LocationDto locationDto);
}