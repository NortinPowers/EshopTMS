package by.tms.eshop.service;

import static by.tms.eshop.utils.Constants.AND_PAGE;
import static by.tms.eshop.utils.Constants.AND_SIZE;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_CART;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_FAVORITES;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCT_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_RESULT_SAVE;
import static by.tms.eshop.utils.Constants.RequestParameters.FAVORITE;
import static by.tms.eshop.utils.Constants.RequestParameters.PRODUCT_PAGE;
import static by.tms.eshop.utils.Constants.RequestParameters.SEARCH;
import static by.tms.eshop.utils.Constants.RequestParameters.TRUE;
import static by.tms.eshop.utils.Constants.SIZE_3;

import by.tms.eshop.dto.CartDto;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.model.Location;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ShopFacadeTest {

    @Autowired
    private ShopFacade shopFacade;

    @MockBean
    private ProductService productService;

    @MockBean
    private CartService cartService;

    @Test
    void getPathFromAddCartByParameters() {
        Long productId = 1L;
        String shopFlagTrue = TRUE;
        String shopFlagElse = "someFlag";
        String location = "someLocation";
        Integer page = 2;
        String extensionPath = AND_PAGE + page;
        Assertions.assertEquals(REDIRECT_TO_CART, shopFacade.getPathFromAddCartByParameters(productId, shopFlagTrue, location, null));
        Assertions.assertEquals(REDIRECT_TO_CART + extensionPath, shopFacade.getPathFromAddCartByParameters(productId, shopFlagTrue, location, page));
        Assertions.assertEquals(REDIRECT_TO_FAVORITES, shopFacade.getPathFromAddCartByParameters(productId, shopFlagElse, FAVORITE, null));
        Assertions.assertEquals(REDIRECT_TO_FAVORITES + extensionPath, shopFacade.getPathFromAddCartByParameters(productId, shopFlagElse, FAVORITE, page));
        Assertions.assertEquals(REDIRECT_TO_SEARCH_RESULT_SAVE, shopFacade.getPathFromAddCartByParameters(productId, shopFlagElse, SEARCH, null));
        Assertions.assertEquals(REDIRECT_TO_SEARCH_RESULT_SAVE + extensionPath, shopFacade.getPathFromAddCartByParameters(productId, shopFlagElse, SEARCH, page));
        Assertions.assertEquals(REDIRECT_TO_PRODUCT_WITH_PARAM + productId, shopFacade.getPathFromAddCartByParameters(productId, shopFlagElse, PRODUCT_PAGE, null));
        Assertions.assertEquals(REDIRECT_TO_PRODUCT_WITH_PARAM + productId + extensionPath, shopFacade.getPathFromAddCartByParameters(productId, shopFlagElse, PRODUCT_PAGE, page));
        String productCategory = "someCategory";
        Mockito.when(productService.getProductCategoryValue(productId)).thenReturn(productCategory);
        Assertions.assertEquals(REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + productCategory + AND_SIZE + SIZE_3, shopFacade.getPathFromAddCartByParameters(productId, shopFlagElse, location, null));
        Assertions.assertEquals(REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + productCategory + AND_SIZE + SIZE_3 + extensionPath, shopFacade.getPathFromAddCartByParameters(productId, shopFlagElse, location, page));
    }

    @Test
    void getFavoriteProducts() {
        ProductDto productDto = ProductDto.builder()
                                          .id(1L)
                                          .price(BigDecimal.TEN)
                                          .name("someName")
                                          .info("someInfo")
                                          .category("someCategory")
                                          .build();
        List<CartDto> cartDtos = List.of(CartDto.builder()
                                                .id(2L)
                                                .productDto(productDto)
                                                 .cart(false)
                                                 .favorite(true)
                                                 .count(3)
                                                .build());
        Long userId = 4L;
        Mockito.when(cartService.getSelectedProducts(userId, Location.FAVORITE)).thenReturn(cartDtos);
        Assertions.assertEquals(List.of(productDto), shopFacade.getFavoriteProducts(userId));
    }
}