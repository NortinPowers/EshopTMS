package by.tms.eshop.controller;

import static by.tms.eshop.test_utils.Constants.PAGE;
import static by.tms.eshop.test_utils.Constants.PRODUCT_ID;
import static by.tms.eshop.test_utils.Constants.TEST_PROPERTY_SOURCE_LOCATIONS;
import static by.tms.eshop.test_utils.ControllerUtils.getCustomUserDetailRoleAdmin;
import static by.tms.eshop.test_utils.ControllerUtils.getCustomUserDetailRoleUser;
import static by.tms.eshop.utils.Constants.AND_PAGE;
import static by.tms.eshop.utils.Constants.Attributes.CART_PRODUCTS;
import static by.tms.eshop.utils.Constants.Attributes.FULL_PRICE;
import static by.tms.eshop.utils.Constants.BUY;
import static by.tms.eshop.utils.Constants.ControllerMappingPath.LOGIN;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_CART;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SOME_ERROR;
import static by.tms.eshop.utils.Constants.MappingPath.SHOPPING_CART;
import static by.tms.eshop.utils.Constants.MappingPath.SUCCESS_BUY;
import static by.tms.eshop.utils.Constants.RequestParameters.FAVORITE;
import static by.tms.eshop.utils.Constants.RequestParameters.ID;
import static by.tms.eshop.utils.Constants.RequestParameters.LOCATION;
import static by.tms.eshop.utils.Constants.RequestParameters.SHOP;
import static by.tms.eshop.utils.Constants.TRUE;
import static by.tms.eshop.utils.ControllerUtils.getProductsPrice;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import by.tms.eshop.dto.CartDto;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.model.Location;
import by.tms.eshop.security.CustomUserDetail;
import by.tms.eshop.service.CartService;
import by.tms.eshop.service.ShopFacade;
import by.tms.eshop.utils.Constants;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = TEST_PROPERTY_SOURCE_LOCATIONS)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${application.base-url}")
    private String baseUrl;

    @MockBean
    private CartService cartService;

    @MockBean
    private ShopFacade shopFacade;

    private final CustomUserDetail customUserDetailRoleAdmin = getCustomUserDetailRoleAdmin();
    private final CustomUserDetail customUserDetailRoleUser = getCustomUserDetailRoleUser();

    @Nested
    class TestShowCardPage {

        @Test
        @WithAnonymousUser
        void test_showCardPage_anonymous_denied() throws Exception {
            mockMvc.perform(get("/cart"))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(redirectedUrl(baseUrl + LOGIN));
        }

        @Test
        void test_showCardPage_roleUser_allowed() throws Exception {
            inspectShowCardPageByUserRole(customUserDetailRoleUser);
        }

        @Test
        void test_showCardPage_roleAdmin_allowed() throws Exception {
            inspectShowCardPageByUserRole(customUserDetailRoleAdmin);
        }

        private void inspectShowCardPageByUserRole(CustomUserDetail customUserDetail) throws Exception {
            BigDecimal price = BigDecimal.valueOf(120);
            ProductDto productDto = ProductDto.builder()
                                              .price(price)
                                              .build();
            int productCount = 2;
            List<CartDto> cartDtos = List.of(CartDto.builder()
                                                    .productDto(productDto)
                                                    .count(productCount)
                                                    .build());
            BigDecimal productsPrice = getProductsPrice(cartDtos);
            BigDecimal expectedPrice = price.multiply(BigDecimal.valueOf(productCount));

            when(cartService.getSelectedProducts(customUserDetail.getUser().getId(), Location.CART)).thenReturn(cartDtos);

            mockMvc.perform(get("/cart").with(user(customUserDetail)))
                   .andExpect(status().isOk())
                   .andExpect(model().attribute(CART_PRODUCTS, cartDtos))
                   .andExpect(model().attribute(FULL_PRICE, productsPrice))
                   .andExpect(model().attribute(FULL_PRICE, equalTo(expectedPrice)))
                   .andExpect(view().name(SHOPPING_CART));
        }
    }

    @Nested
    class TestShowCartProcessingPage {

        @Test
        @WithAnonymousUser
        void test_showCartProcessingPage_anonymous_denied() throws Exception {
            mockMvc.perform(post("/cart-processing")
                                    .with(csrf())
                                    .param(BUY, BUY))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(redirectedUrl(baseUrl + LOGIN));
        }

        @Test
        void test_showCartProcessingPage_roleUser_allowed_paramBuy() throws Exception {
            inspectShowCartProcessingPageByRole(customUserDetailRoleUser);
        }

        @Test
        void test_showCartProcessingPage_roleAdmin_allowed_paramBuy() throws Exception {
            inspectShowCartProcessingPageByRole(customUserDetailRoleAdmin);
        }

        @Test
        void test_showCartProcessingPage_roleUser_allowed_WithoutParam() throws Exception {
            inspectShowCartProcessingPageByRoleWithoutParam(customUserDetailRoleUser);
        }

        @Test
        void test_showCartProcessingPage_roleAdmin_allowed__WithoutParam() throws Exception {
            inspectShowCartProcessingPageByRoleWithoutParam(customUserDetailRoleAdmin);
        }

        private void inspectShowCartProcessingPageByRoleWithoutParam(CustomUserDetail customUserDetail) throws Exception {
            mockMvc.perform(post("/cart-processing")
                                    .with(user(customUserDetail))
                                    .with(csrf()))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(view().name(REDIRECT_TO_SOME_ERROR));
        }

        private void inspectShowCartProcessingPageByRole(CustomUserDetail customUserDetail) throws Exception {
            ModelAndView modelAndView = new ModelAndView(SUCCESS_BUY);
            when(shopFacade.getPageByParam(BUY)).thenReturn(modelAndView);

            mockMvc.perform(post("/cart-processing")
                                    .with(user(customUserDetail))
                                    .with(csrf())
                                    .param(BUY, BUY))
                   .andExpect(status().isOk())
                   .andExpect(view().name(SUCCESS_BUY));
        }
    }

    @Nested
    class TestAddProductToCart {

        @Test
        @WithAnonymousUser
        void test_addProductToCart_anonymous_denied() throws Exception {
            mockMvc.perform(get("/add-cart"))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(redirectedUrl(baseUrl + LOGIN));
        }

        @Test
        void test_addProductToCart_roleUser_allowed_allParam() throws Exception {
            inspectAddProductToCartByRoleWithAllParam(customUserDetailRoleUser);
        }

        @Test
        void test_addProductToCart_roleAdmin_allowed_allParam() throws Exception {
            inspectAddProductToCartByRoleWithAllParam(customUserDetailRoleAdmin);
        }

        @Test
        void test_addProductToCart_roleUser_allowed_allRequiredParam() throws Exception {
            inspectAddProductToCartByRoleWithAllRequiredParam(customUserDetailRoleUser);
        }

        @Test
        void test_addProductToCart_roleUser_roleAdmin_allowed_allRequiredParam() throws Exception {
            inspectAddProductToCartByRoleWithAllRequiredParam(customUserDetailRoleAdmin);
        }

        @Test
        void test_addProductToCart_roleUser_allowed_notAllRequiredParam() throws Exception {
            inspectAddProductToCartByRoleWithNotAllRequiredParam(customUserDetailRoleUser);
        }

        @Test
        void test_addProductToCart_roleAdmin_allowed_notAllRequiredParam() throws Exception {
            inspectAddProductToCartByRoleWithNotAllRequiredParam(customUserDetailRoleAdmin);
        }

        private void inspectAddProductToCartByRoleWithAllParam(CustomUserDetail customUserDetail) throws Exception {
            doNothing().when(cartService).addSelectedProduct(customUserDetail.getUser().getId(), PRODUCT_ID, Location.CART);
            when(shopFacade.getPathFromAddCartByParameters(PRODUCT_ID, TRUE, FAVORITE, PAGE)).thenReturn(REDIRECT_TO_CART + AND_PAGE + PAGE);

            mockMvc.perform(get("/add-cart")
                                    .with(user(customUserDetail))
                                    .param(ID, PRODUCT_ID.toString())
                                    .param(SHOP, TRUE)
                                    .param(LOCATION, FAVORITE)
                                    .param(Constants.PAGE, PAGE.toString()))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(view().name(REDIRECT_TO_CART + AND_PAGE + PAGE));
        }

        private void inspectAddProductToCartByRoleWithAllRequiredParam(CustomUserDetail customUserDetail) throws Exception {
            doNothing().when(cartService).addSelectedProduct(customUserDetail.getUser().getId(), PRODUCT_ID, Location.CART);
            when(shopFacade.getPathFromAddCartByParameters(PRODUCT_ID, TRUE, FAVORITE, null)).thenReturn(REDIRECT_TO_CART);

            mockMvc.perform(get("/add-cart")
                                    .with(user(customUserDetail))
                                    .param(ID, PRODUCT_ID.toString())
                                    .param(SHOP, TRUE)
                                    .param(LOCATION, FAVORITE))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(view().name(REDIRECT_TO_CART));
        }

        private void inspectAddProductToCartByRoleWithNotAllRequiredParam(CustomUserDetail customUserDetail) throws Exception {
            mockMvc.perform(get("/add-cart")
                                    .with(user(customUserDetail))
                                    .param(SHOP, TRUE)
                                    .param(LOCATION, FAVORITE))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(view().name(REDIRECT_TO_SOME_ERROR));
        }
    }

    @Nested
    class TestDeleteProductFromCart {

        @Test
        @WithAnonymousUser
        void test_deleteProductFromCart_anonymous_denied() throws Exception {
            mockMvc.perform(get("/delete-cart"))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(redirectedUrl(baseUrl + LOGIN));
        }

        @Test
        void test_deleteProductFromCart_roleUser_allowed_allRequiredParam() throws Exception {
            inspectDeleteProductFromCartByRoleWithAllRequiredParam(customUserDetailRoleUser);
        }

        @Test
        void test_deleteProductFromCart_roleUser_roleAdmin_allowed_AllRequiredParam() throws Exception {
            inspectDeleteProductFromCartByRoleWithAllRequiredParam(customUserDetailRoleAdmin);
        }

        @Test
        void test_deleteProductFromCart_roleUser_allowed_notAllRequiredParam() throws Exception {
            inspectDeleteProductFromCartByRoleWithNotAllRequiredParam(customUserDetailRoleUser);
        }

        @Test
        void test_deleteProductFromCart_roleAdmin_allowed_notAllRequiredParam() throws Exception {
            inspectDeleteProductFromCartByRoleWithNotAllRequiredParam(customUserDetailRoleAdmin);
        }

        private void inspectDeleteProductFromCartByRoleWithAllRequiredParam(CustomUserDetail customUserDetail) throws Exception {
            doNothing().when(cartService).deleteProduct(customUserDetail.getUser().getId(), PRODUCT_ID, Location.CART);

            mockMvc.perform(get("/delete-cart")
                                    .with(user(customUserDetail))
                                    .param(ID, PRODUCT_ID.toString()))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(view().name(REDIRECT_TO_CART));
        }

        private void inspectDeleteProductFromCartByRoleWithNotAllRequiredParam(CustomUserDetail customUserDetail) throws Exception {
            mockMvc.perform(get("/delete-cart")
                                    .with(user(customUserDetail)))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(view().name(REDIRECT_TO_SOME_ERROR));
        }
    }
}
