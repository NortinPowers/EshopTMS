package by.tms.eshop.repository;

import by.tms.eshop.dto.LocationDto;
import by.tms.eshop.dto.ProductDto;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;

public interface CartCustomizedRepository {

    void addSelectedProduct(Long userId, Long productId, LocationDto locationDto);

    void deleteProduct(Long userId, Long productId, LocationDto locationDto);

    List<ImmutablePair<ProductDto, Integer>> getSelectedProducts(Long userId, LocationDto locationDto);

    boolean checkProduct(Long userId, Long productId, LocationDto locationDto);

    Integer getCartProductCount(Long userId, Long productId);

    List<ProductDto> getPurchasedProducts(Long userId, LocationDto locationDto);
}