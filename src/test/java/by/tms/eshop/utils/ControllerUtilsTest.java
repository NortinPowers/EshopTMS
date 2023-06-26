package by.tms.eshop.utils;

import static by.tms.eshop.test_utils.Constants.EXTENSION_PATH;
import static by.tms.eshop.test_utils.Constants.LOCATION;
import static by.tms.eshop.test_utils.Constants.PAGE;
import static by.tms.eshop.test_utils.Constants.PRODUCT_CATEGORY;
import static by.tms.eshop.test_utils.Constants.PRODUCT_ID;
import static by.tms.eshop.utils.Constants.AND_SIZE;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCT_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_RESULT_SAVE;
import static by.tms.eshop.utils.Constants.PRODUCT_PAGE_SIZE;
import static by.tms.eshop.utils.Constants.RequestParameters.PRODUCT_PAGE;
import static by.tms.eshop.utils.Constants.RequestParameters.SEARCH;
import static by.tms.eshop.utils.ControllerUtils.getPathFromAddFavoriteByParameters;
import static by.tms.eshop.utils.ControllerUtils.getProductsPrice;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import by.tms.eshop.dto.CartDto;
import by.tms.eshop.dto.ProductDto;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ControllerUtilsTest {

    private ProductDto productOne;
    private ProductDto productTwo;
    private CartDto countOne;
    private CartDto countTwo;

    @Test
    void test_getPathFromAddFavoriteByParameters_toSearch_pageNull() {
        assertEquals(REDIRECT_TO_SEARCH_RESULT_SAVE, getPathFromAddFavoriteByParameters(PRODUCT_ID, SEARCH, PRODUCT_CATEGORY, null));
    }

    @Test
    void test_getPathFromAddFavoriteByParameters_toSearch_pageNotNull() {
        assertEquals(REDIRECT_TO_SEARCH_RESULT_SAVE + EXTENSION_PATH, getPathFromAddFavoriteByParameters(PRODUCT_ID, SEARCH, PRODUCT_CATEGORY, PAGE));
    }

    @Test
    void test_getPathFromAddFavoriteByParameters_toProduct_pageNull() {
        assertEquals(REDIRECT_TO_PRODUCT_WITH_PARAM + PRODUCT_ID, getPathFromAddFavoriteByParameters(PRODUCT_ID, PRODUCT_PAGE, PRODUCT_CATEGORY, null));
    }

    @Test
    void test_getPathFromAddFavoriteByParameters_toProduct_pageNotNull() {
        assertEquals(REDIRECT_TO_PRODUCT_WITH_PARAM + PRODUCT_ID + EXTENSION_PATH, getPathFromAddFavoriteByParameters(PRODUCT_ID, PRODUCT_PAGE, PRODUCT_CATEGORY, PAGE));
    }

    @Test
    void test_getPathFromAddFavoriteByParameters_toProducts_pageNull() {
        assertEquals(REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + PRODUCT_CATEGORY + AND_SIZE + PRODUCT_PAGE_SIZE, getPathFromAddFavoriteByParameters(PRODUCT_ID, LOCATION, PRODUCT_CATEGORY, null));
    }

    @Test
    void test_getPathFromAddFavoriteByParameters_toProducts_pageNotNull() {
        assertEquals(REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + PRODUCT_CATEGORY + AND_SIZE + PRODUCT_PAGE_SIZE + EXTENSION_PATH, getPathFromAddFavoriteByParameters(PRODUCT_ID, LOCATION, PRODUCT_CATEGORY, PAGE));
    }

    @Test
    void test_getProductsPrice_success() {
        productOne = getProduct(BigDecimal.TEN);
        productTwo = getProduct(BigDecimal.TWO);
        countOne = getCart(productOne, 2);
        countTwo = getCart(productTwo, 4);
        List<CartDto> carts = List.of(countOne, countTwo);

        assertEquals(BigDecimal.valueOf(28), getProductsPrice(carts));
    }

    @Test
    void test_getProductsPrice_failure() {
        productOne = getProduct(BigDecimal.ONE);
        productTwo = getProduct(BigDecimal.TWO);
        countOne = getCart(productOne, 1);
        countTwo = getCart(productTwo, 2);
        List<CartDto> carts = List.of(countOne, countTwo);

        assertNotEquals(BigDecimal.valueOf(10), getProductsPrice(carts));
    }

    private CartDto getCart(ProductDto product, Integer count) {
        return CartDto.builder()
                      .productDto(product)
                      .count(count)
                      .build();
    }

    private ProductDto getProduct(BigDecimal price) {
        return ProductDto.builder()
                         .price(price)
                         .build();
    }
}
