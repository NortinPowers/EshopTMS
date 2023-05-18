package by.tms.eshop.repository.impl;

import by.tms.eshop.domain.Cart;
import by.tms.eshop.dto.LocationDto;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static by.tms.eshop.utils.Constants.QueryParameter.USER_ID;
import static by.tms.eshop.utils.RepositoryJdbcUtils.getCart;
import static by.tms.eshop.utils.RepositoryJdbcUtils.getCarts;
import static by.tms.eshop.utils.RepositoryJdbcUtils.getCurrentCart;
import static by.tms.eshop.utils.RepositoryJdbcUtils.getImmutablePairsProductDtoCount;
import static by.tms.eshop.utils.RepositoryJdbcUtils.getModifyCount;
import static by.tms.eshop.utils.RepositoryJdbcUtils.isProductNotIncluded;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {

    private final SessionFactory sessionFactory;

    private static final String GET_CART_PRODUCTS_BY_USER_ID = "FROM Cart WHERE user.id = :userId AND cart = true";
    private static final String GET_FAVORITE_PRODUCTS_BY_USER_ID = "FROM Cart WHERE user.id = :userId AND favorite = true";
    private static final String GET_CURRENT_CART = "FROM Cart WHERE user.id = :userId AND product.id = :productId AND cart = true";
    private static final String GET_CURRENT_FAVORITE = "FROM Cart WHERE user.id = :userId AND product.id = :productId AND favorite = true";
    private static final String GET_BUYING_PRODUCT = "FROM Cart WHERE user.id = :userId AND cart = true";

    @Override
    public void addSelectedProduct(Long userId, Long productId, LocationDto locationDto) {
        if (locationDto.isFavorite()) {
            if (checkProduct(userId, productId, locationDto)) {
                addProduct(userId, productId, locationDto);
            }
        } else {
            if (checkProduct(userId, productId, locationDto)) {
                addProduct(userId, productId, locationDto);
            } else {
                modifyProductCount(userId, productId, true);
            }
        }
    }

    @Override
    public void deleteProduct(Long userId, Long productId, LocationDto locationDto) {
        if (locationDto.isFavorite()) {
            deleteProductByMark(userId, productId, GET_CURRENT_FAVORITE);
        } else {
            Integer productCount = getCartProductCount(userId, productId);
            if (productCount > 1) {
                modifyProductCount(userId, productId, false);
            } else {
                deleteProductByMark(userId, productId, GET_CURRENT_CART);
            }
        }
    }

    @Override
    public List<ImmutablePair<ProductDto, Integer>> getSelectedProducts(Long userId, LocationDto locationDto) {
        String query = locationDto.isCart() ? GET_CART_PRODUCTS_BY_USER_ID : GET_FAVORITE_PRODUCTS_BY_USER_ID;
        Session session = sessionFactory.getCurrentSession();
        List<Cart> carts = session.createQuery(query, Cart.class)
                .setParameter(USER_ID, userId)
                .getResultList();
        return getImmutablePairsProductDtoCount(carts);
    }

    @Override
    public boolean checkProduct(Long userId, Long productId, LocationDto locationDto) {
        List<ProductDto> productsDto = getProducts(userId, locationDto);
        return isProductNotIncluded(productId, productsDto);
    }

    @Override
    public Integer getCartProductCount(Long userId, Long productId) {
        Session session = sessionFactory.getCurrentSession();
        return getCarts(userId, productId, GET_CURRENT_CART, session).stream()
                .map(Cart::getCount)
                .findAny()
                .orElse(0);
    }

    @Override
    public void deleteCartProductsAfterBuy(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery(GET_BUYING_PRODUCT, Cart.class)
                .setParameter(USER_ID, userId)
                .getResultList().forEach(session::remove);
    }

    @Override
    public List<ProductDto> getPurchasedProducts(Long userId, LocationDto locationDto) {
        List<ProductDto> products = new ArrayList<>();
        List<ImmutablePair<ProductDto, Integer>> productWithCount = getSelectedProducts(userId, locationDto);
        for (Pair<ProductDto, Integer> productIntegerPair : productWithCount) {
            Integer count = productIntegerPair.getRight();
            while (count > 0) {
                products.add(productIntegerPair.getLeft());
                count--;
            }
        }
        return products;
    }

    private void addProduct(Long userId, Long productId, LocationDto locationDto) {
        Cart cart = getCart(userId, productId, locationDto);
        Session session = sessionFactory.getCurrentSession();
        session.persist(cart);
    }

    private void modifyProductCount(Long userId, Long productId, boolean up) {
        Integer productCount = getCartProductCount(userId, productId);
        productCount = getModifyCount(up, productCount);
        Session session = sessionFactory.getCurrentSession();
        Cart cart = getCurrentCart(userId, productId, GET_CURRENT_CART, session);
        cart.setCount(productCount);
        session.merge(cart);
    }

    private List<ProductDto> getProducts(Long userId, LocationDto locationDto) {
        return getSelectedProducts(userId, locationDto).stream()
                .map(Pair::getLeft)
                .collect(Collectors.toList());
    }

    private void deleteProductByMark(Long userId, Long productId, String query) {
        Session session = sessionFactory.getCurrentSession();
        Cart cart = getCurrentCart(userId, productId, query, session);
        session.remove(cart);
    }
}