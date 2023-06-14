package by.tms.eshop.service;

import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.model.Location;
import java.util.List;
import org.apache.commons.lang3.tuple.ImmutablePair;

public interface CartService {

    void addSelectedProduct(Long userId, Long productId, Location location);
//    void addSelectedProduct(Long userId, Long productId, LocationDto locationDto);

    List<ImmutablePair<ProductDto, Integer>> getSelectedProducts(Long userId, Location location);
//    List<ImmutablePair<ProductDto, Integer>> getSelectedProducts(Long userId, LocationDto locationDto);

    void deleteProduct(Long userId, Long productId, Location location);
//    void deleteProduct(Long userId, Long productId, LocationDto locationDto);

    void deleteCartProductsAfterBuy(Long userId);

    List<ProductDto> getPurchasedProducts(Long userId, Location location);
//    List<ProductDto> getPurchasedProducts(Long userId, LocationDto locationDto);
}
