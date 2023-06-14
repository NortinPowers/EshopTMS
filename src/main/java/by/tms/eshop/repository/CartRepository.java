package by.tms.eshop.repository;

import by.tms.eshop.domain.Cart;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.model.Location;
import java.util.List;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>, CartCustomizedRepository {

    void addSelectedProduct(Long userId, Long productId, Location location);
//    void addSelectedProduct(Long userId, Long productId, LocationDto locationDto);

    void deleteProduct(Long userId, Long productId, Location location);
//    void deleteProduct(Long userId, Long productId, LocationDto locationDto);

    List<ImmutablePair<ProductDto, Integer>> getSelectedProducts(Long userId, Location location);
//    List<ImmutablePair<ProductDto, Integer>> getSelectedProducts(Long userId, LocationDto locationDto);

    boolean checkProduct(Long userId, Long productId, Location location);
//    boolean checkProduct(Long userId, Long productId, LocationDto locationDto);

    Integer getCartProductCount(Long userId, Long productId);

//    @Query("FROM Cart WHERE user.id = :userId AND product.id = :productId AND cart = true")
//    Integer getCartProductCountTest(Long userId, Long productId);
//
//    List<Cart> findCartByUserIdAndProductIdAndCart(Long userId, Long productId, boolean cart);

    void deleteCartByUserIdAndCart(Long userId, Boolean cart);

    List<ProductDto> getPurchasedProducts(Long userId, Location location);
//    List<ProductDto> getPurchasedProducts(Long userId, LocationDto locationDto);
}
