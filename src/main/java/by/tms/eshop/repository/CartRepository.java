package by.tms.eshop.repository;

import by.tms.eshop.domain.Cart;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("FROM Cart WHERE user.id = :userId AND product.id = :productId AND favorite = true")
    Optional<Cart> getUserFavorite(Long userId, Long productId);

    @Query("FROM Cart WHERE user.id = :userId AND product.id = :productId AND cart = true")
    Optional<Cart> getUserCart(Long userId, Long productId);

    @Query("FROM Cart WHERE user.id = :userId AND favorite = true")
    List<Cart> getFavoriteProducts(Long userId);

    @Query("FROM Cart WHERE user.id = :userId AND cart = true order by product.name")
    List<Cart> getCartProducts(Long userId);

    void deleteCartByUserIdAndCart(Long userId, Boolean cart);

    @Query("SELECT new map(product.id as productId, COUNT(product.id) as count) FROM Cart WHERE favorite = true GROUP BY product.id ORDER BY COUNT(product.id) DESC LIMIT 3")
    List<Map<Long, Long>> getMostFavorite();
}
