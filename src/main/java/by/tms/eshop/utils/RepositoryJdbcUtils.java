package by.tms.eshop.utils;

import by.tms.eshop.domain.Cart;
import by.tms.eshop.domain.Order;
import by.tms.eshop.domain.Product;
import by.tms.eshop.domain.User;
import by.tms.eshop.dto.LocationDto;
import by.tms.eshop.dto.ProductDto;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.hibernate.Session;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static by.tms.eshop.utils.Constants.ALL;
import static by.tms.eshop.utils.Constants.QueryParameter.CONDITION;
import static by.tms.eshop.utils.Constants.QueryParameter.MAX_PRICE;
import static by.tms.eshop.utils.Constants.QueryParameter.MIN_PRICE;
import static by.tms.eshop.utils.Constants.QueryParameter.PRODUCT_ID;
import static by.tms.eshop.utils.Constants.QueryParameter.USER_ID;

@UtilityClass
public class RepositoryJdbcUtils {

    public static Integer getModifyCount(boolean up, Integer productCount) {
        return up ? ++productCount : --productCount;
    }

    public static boolean isProductNotIncluded(Long productId, List<ProductDto> products) {
        return products.stream()
                .filter(product -> Objects.equals(product.getId(), productId))
                .findAny()
                .isEmpty();
    }

    public static String getQueryDependType(String category, String query) {
        String fullQuery;
        if (!ALL.equals(category)) {
            fullQuery = query + " AND productCategory.category = '" + category + "' ORDER BY id";
        } else {
            fullQuery = query + " ORDER BY id";
        }
        return fullQuery;
    }

    public static Order getOrder(String order, Long id) {
        return Order.builder()
                .name(order)
                .date(LocalDate.now())
                .user(User.builder()
                        .id(id)
                        .build())
                .build();
    }

    public static Cart getCart(Long userId, Long productId, LocationDto locationDto) {
        return Cart.builder()
                .user(User.builder()
                        .id(userId)
                        .build())
                .product(Product.builder()
                        .id(productId)
                        .build())
                .favorite(locationDto.isFavorite())
                .cart(locationDto.isCart())
                .count(1)
                .build();
    }

    public static Cart getCurrentCart(Long userId, Long productId, String query, Session session) {
        return session.createQuery(query, Cart.class)
                .setParameter(USER_ID, userId)
                .setParameter(PRODUCT_ID, productId)
                .getSingleResult();
    }

    public static List<Cart> getCarts(Long userId, Long productId, String query, Session session) {
        return session.createQuery(query, Cart.class)
                .setParameter(USER_ID, userId)
                .setParameter(PRODUCT_ID, productId)
                .getResultList();
    }

    public static List<Product> getSearchProductsByCondition(String condition, String query, Session session) {
        return session.createQuery(query, Product.class)
                .setParameter(CONDITION, condition)
                .getResultList();
    }

    public static List<Product> getSearchProductsByPrice(BigDecimal minPrice, BigDecimal maxPrice, String query, Session session) {
        return session.createQuery(query, Product.class)
                .setParameter(MIN_PRICE, minPrice)
                .setParameter(MAX_PRICE, maxPrice)
                .getResultList();
    }

    public static List<ImmutablePair<ProductDto, Integer>> getImmutablePairsProductDtoCount(List<Cart> carts) {
        List<ImmutablePair<ProductDto, Integer>> pairs = new ArrayList<>();
        carts.forEach(cart -> pairs.add(new ImmutablePair<>(ProductDto.builder()
                .id(cart.getProduct().getId())
                .name(cart.getProduct().getName())
                .category(cart.getProduct().getProductCategory().getCategory())
                .price(cart.getProduct().getPrice())
                .info(cart.getProduct().getInfo())
                .build(), cart.getCount())));
        return pairs;
    }

    public static <T> PagedListHolder<T> getPagedListHolder(Pageable pageable, List<T> objects) {
        PagedListHolder<T> pageHolder = new PagedListHolder<>(objects);
        pageHolder.setPageSize(pageable.getPageSize());
        pageHolder.setPage(pageable.getPageNumber());
        return pageHolder;
    }
}