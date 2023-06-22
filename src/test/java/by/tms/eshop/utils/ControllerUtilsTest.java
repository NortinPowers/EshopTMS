package by.tms.eshop.utils;

import static by.tms.eshop.utils.Constants.AND_PAGE;
import static by.tms.eshop.utils.Constants.AND_SIZE;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCT_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_RESULT_SAVE;
import static by.tms.eshop.utils.Constants.RequestParameters.PRODUCT_PAGE;
import static by.tms.eshop.utils.Constants.RequestParameters.SEARCH;
import static by.tms.eshop.utils.Constants.SIZE_3;

import by.tms.eshop.dto.CartDto;
import by.tms.eshop.dto.ProductDto;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ControllerUtilsTest {

    private final Long productId = 1L;
    private final Integer page = 2;
    private final String extensionPath = AND_PAGE + page;
    private final String productCategory = "someCategory";

    @Test
    void getPathFromAddFavoriteByParametersToSearch() {
        Assertions.assertEquals(REDIRECT_TO_SEARCH_RESULT_SAVE, ControllerUtils.getPathFromAddFavoriteByParameters(productId, SEARCH, productCategory, null));
        Assertions.assertEquals(REDIRECT_TO_SEARCH_RESULT_SAVE + extensionPath, ControllerUtils.getPathFromAddFavoriteByParameters(productId, SEARCH, productCategory, page));
    }

    @Test
    void getPathFromAddFavoriteByParametersToProduct() {
        Assertions.assertEquals(REDIRECT_TO_PRODUCT_WITH_PARAM + productId, ControllerUtils.getPathFromAddFavoriteByParameters(productId, PRODUCT_PAGE, productCategory, null));
        Assertions.assertEquals(REDIRECT_TO_PRODUCT_WITH_PARAM + productId + extensionPath, ControllerUtils.getPathFromAddFavoriteByParameters(productId, PRODUCT_PAGE, productCategory, page));
    }

    @Test
    void getPathFromAddFavoriteByParametersToProducts() {
        String location = "someLocation";
        Assertions.assertEquals(REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + productCategory + AND_SIZE + SIZE_3, ControllerUtils.getPathFromAddFavoriteByParameters(productId, location, productCategory, null));
        Assertions.assertEquals(REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + productCategory + AND_SIZE + SIZE_3 + extensionPath, ControllerUtils.getPathFromAddFavoriteByParameters(productId, location, productCategory, page));
    }

    @Test
    void getProductsPrice() {
        ProductDto productOne = getProduct(BigDecimal.TEN);
        ProductDto productTwo = getProduct(BigDecimal.TWO);
        CartDto countOne = getCart(productOne, 2);
        CartDto countTwo = getCart(productTwo, 4);
        List<CartDto> carts = List.of(countOne, countTwo);
        Assertions.assertEquals(BigDecimal.valueOf(28), ControllerUtils.getProductsPrice(carts));

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
