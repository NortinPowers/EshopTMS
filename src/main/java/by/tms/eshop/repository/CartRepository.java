package by.tms.eshop.repository;

import by.tms.eshop.domain.Cart;
import by.tms.eshop.dto.LocationDto;
import by.tms.eshop.dto.ProductDto;
import java.util.List;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>, CartCustomizedRepository {

    void addSelectedProduct(Long userId, Long productId, LocationDto locationDto);

    void deleteProduct(Long userId, Long productId, LocationDto locationDto);

    List<ImmutablePair<ProductDto, Integer>> getSelectedProducts(Long userId, LocationDto locationDto);

    boolean checkProduct(Long userId, Long productId, LocationDto locationDto);

    Integer getCartProductCount(Long userId, Long productId);

    void deleteCartByUserIdAndCart(Long userId, Boolean cart);

    List<ProductDto> getPurchasedProducts(Long userId, LocationDto locationDto);
}
