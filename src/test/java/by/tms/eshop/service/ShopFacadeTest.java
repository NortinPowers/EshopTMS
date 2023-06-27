package by.tms.eshop.service;

import static by.tms.eshop.test_utils.Constants.EXTENSION_PATH;
import static by.tms.eshop.test_utils.Constants.LOCATION;
import static by.tms.eshop.test_utils.Constants.PAGE;
import static by.tms.eshop.test_utils.Constants.PRODUCT_CATEGORY;
import static by.tms.eshop.test_utils.Constants.PRODUCT_ID;
import static by.tms.eshop.test_utils.Constants.ROLE_USER;
import static by.tms.eshop.test_utils.Constants.SECRET_QWERTY;
import static by.tms.eshop.utils.Constants.AND_SIZE;
import static by.tms.eshop.utils.Constants.Attributes.PRODUCTS;
import static by.tms.eshop.utils.Constants.Attributes.PRODUCT_CATEGORIES;
import static by.tms.eshop.utils.Constants.Attributes.USER;
import static by.tms.eshop.utils.Constants.BUY;
import static by.tms.eshop.utils.Constants.MappingPath.ACCOUNT;
import static by.tms.eshop.utils.Constants.MappingPath.ADMIN_INFO;
import static by.tms.eshop.utils.Constants.MappingPath.EDIT;
import static by.tms.eshop.utils.Constants.MappingPath.ESHOP;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_CART;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_FAVORITES;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCT_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_RESULT_SAVE;
import static by.tms.eshop.utils.Constants.MappingPath.SUCCESS_BUY;
import static by.tms.eshop.utils.Constants.PRODUCT_PAGE_SIZE;
import static by.tms.eshop.utils.Constants.RequestParameters.FAVORITE;
import static by.tms.eshop.utils.Constants.RequestParameters.PRODUCT_PAGE;
import static by.tms.eshop.utils.Constants.RequestParameters.SEARCH;
import static by.tms.eshop.utils.Constants.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.tms.eshop.domain.User;
import by.tms.eshop.dto.CartDto;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.dto.RoleDto;
import by.tms.eshop.dto.UserFormDto;
import by.tms.eshop.mapper.UserMapper;
import by.tms.eshop.model.Location;
import by.tms.eshop.security.CustomUserDetail;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.ModelAndView;

@SpringBootTest
class ShopFacadeTest {

    @Autowired
    private ShopFacade shopFacade;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductCategoryService productCategoryService;

    @MockBean
    private CartService cartService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private UserService userService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private SecurityContext securityContext;

    @MockBean
    Authentication authentication;

    @MockBean
    CustomUserDetail customUserDetail;

    @MockBean
    User userMock;

    private final String shopFlagElse = "someFlag";
    private final String shopFlagTrue = TRUE;
    private final User user = User.builder().build();
    private final UserFormDto userFormDto = UserFormDto.builder().build();
    private ModelAndView modelAndView = new ModelAndView();

    @Nested
    class PathFromAddCart {

        @Test
        void test_getPathFromAddCartByParameters_toCart_pageNull() {
            assertEquals(REDIRECT_TO_CART, shopFacade.getPathFromAddCartByParameters(PRODUCT_ID, shopFlagTrue, LOCATION, null));
        }

        @Test
        void test_getPathFromAddCartByParameters_toCart_pageNotNull() {
            assertEquals(REDIRECT_TO_CART + EXTENSION_PATH, shopFacade.getPathFromAddCartByParameters(PRODUCT_ID, shopFlagTrue, LOCATION, PAGE));
        }

        @Test
        void test_getPathFromAddCartByParameters_toFavorites_pageNull() {
            assertEquals(REDIRECT_TO_FAVORITES, shopFacade.getPathFromAddCartByParameters(PRODUCT_ID, shopFlagElse, FAVORITE, null));
        }

        @Test
        void test_getPathFromAddCartByParameters_toFavorites_pageNotNull() {
            assertEquals(REDIRECT_TO_FAVORITES + EXTENSION_PATH, shopFacade.getPathFromAddCartByParameters(PRODUCT_ID, shopFlagElse, FAVORITE, PAGE));
        }

        @Test
        void test_getPathFromAddCartByParameters_toSearch_pageNull() {
            assertEquals(REDIRECT_TO_SEARCH_RESULT_SAVE, shopFacade.getPathFromAddCartByParameters(PRODUCT_ID, shopFlagElse, SEARCH, null));
        }

        @Test
        void test_getPathFromAddCartByParameters_toSearch_pageNotNull() {
            assertEquals(REDIRECT_TO_SEARCH_RESULT_SAVE + EXTENSION_PATH, shopFacade.getPathFromAddCartByParameters(PRODUCT_ID, shopFlagElse, SEARCH, PAGE));
        }

        @Test
        void test_getPathFromAddCartByParameters_toProduct_pageNull() {
            assertEquals(REDIRECT_TO_PRODUCT_WITH_PARAM + PRODUCT_ID, shopFacade.getPathFromAddCartByParameters(PRODUCT_ID, shopFlagElse, PRODUCT_PAGE, null));
        }

        @Test
        void test_getPathFromAddCartByParameters_toProduct_pageNotNull() {
            assertEquals(REDIRECT_TO_PRODUCT_WITH_PARAM + PRODUCT_ID + EXTENSION_PATH, shopFacade.getPathFromAddCartByParameters(PRODUCT_ID, shopFlagElse, PRODUCT_PAGE, PAGE));
        }


        @Test
        void test_getPathFromAddCartByParameters_toProducts_pageNull() {
            when(productService.getProductCategoryValue(PRODUCT_ID)).thenReturn(PRODUCT_CATEGORY);

            assertEquals(REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + PRODUCT_CATEGORY + AND_SIZE + PRODUCT_PAGE_SIZE, shopFacade.getPathFromAddCartByParameters(PRODUCT_ID, shopFlagElse, LOCATION, null));
            verify(productService, atLeastOnce()).getProductCategoryValue(any());
        }

        @Test
        void test_getPathFromAddCartByParameters_toProducts_pageNotNull() {
            when(productService.getProductCategoryValue(PRODUCT_ID)).thenReturn(PRODUCT_CATEGORY);

            assertEquals(REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + PRODUCT_CATEGORY + AND_SIZE + PRODUCT_PAGE_SIZE + EXTENSION_PATH, shopFacade.getPathFromAddCartByParameters(PRODUCT_ID, shopFlagElse, LOCATION, PAGE));
            verify(productService, atLeastOnce()).getProductCategoryValue(any());
        }
    }

    @Test
    void test_getFavoriteProducts() {
        Long userId = 4L;
        ProductDto productDto = ProductDto.builder()
                                          .id(1L)
                                          .price(BigDecimal.TEN)
                                          .name("someName")
                                          .info("someInfo")
                                          .category(PRODUCT_CATEGORY)
                                          .build();
        List<CartDto> cartDtos = List.of(CartDto.builder()
                                                .id(2L)
                                                .productDto(productDto)
                                                .cart(false)
                                                .favorite(true)
                                                .count(3)
                                                .build());

        when(cartService.getSelectedProducts(userId, Location.FAVORITE)).thenReturn(cartDtos);

        assertEquals(List.of(productDto), shopFacade.getFavoriteProducts(userId));
        verify(cartService, atLeastOnce()).getSelectedProducts(any(), any());
    }

    @Test
    void test_createUser_setRoleAndPassword() {
        var captor = ArgumentCaptor.forClass(UserFormDto.class);
        userFormDto.setPassword("qwerty");

        when(passwordEncoder.encode(userFormDto.getPassword())).thenReturn(SECRET_QWERTY);
        when(roleService.getRole(ROLE_USER)).thenReturn(RoleDto.builder().role(ROLE_USER).build());
        when(userMapper.convetrToUser(userFormDto)).thenReturn(user);
        doNothing().when(userService).addUser(user);
        shopFacade.createUser(userFormDto);

        verify(userMapper).convetrToUser(captor.capture());

        UserFormDto value = captor.getValue();
        assertEquals(ROLE_USER, value.getRoleDto().getRole());
        assertEquals(SECRET_QWERTY, value.getPassword());
    }

    @Test
    public void test_EditUser_setNameAndSurname() {
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(customUserDetail);
        when(customUserDetail.getUser()).thenReturn(userMock);
        shopFacade.editUser(userFormDto);

        verify(userMock).setName(userFormDto.getName());
        verify(userMock).setSurname(userFormDto.getSurname());
        verify(userService).addUser(userMock);

        SecurityContextHolder.clearContext();
    }

    @Test
    void test_carriesPurchase() {
        List<ProductDto> productsDto = List.of(ProductDto.builder().build());
        Long userId = 1L;
        Location location = Location.CART;

        when(cartService.getPurchasedProducts(userId, location)).thenReturn(productsDto);
        doNothing().when(orderService).saveUserOrder(userId, productsDto);
        doNothing().when(cartService).deleteCartProductsAfterBuy(userId);
        shopFacade.carriesPurchase(userId);

        verify(cartService, atLeastOnce()).getPurchasedProducts(userId, location);
        verify(orderService, atLeastOnce()).saveUserOrder(userId, productsDto);
        verify(cartService, atLeastOnce()).deleteCartProductsAfterBuy(userId);
    }

    @Nested
    class PathFromAddFavorite {

        @Test
        void test_getPathFromAddFavoriteByParameters_toSearch_pageNull() {
            assertEquals(REDIRECT_TO_SEARCH_RESULT_SAVE, shopFacade.getPathFromAddFavoriteByParameters(PRODUCT_ID, SEARCH, PRODUCT_CATEGORY, null));
        }

        @Test
        void test_getPathFromAddFavoriteByParameters_toSearch_pageNotNull() {
            assertEquals(REDIRECT_TO_SEARCH_RESULT_SAVE + EXTENSION_PATH, shopFacade.getPathFromAddFavoriteByParameters(PRODUCT_ID, SEARCH, PRODUCT_CATEGORY, PAGE));
        }

        @Test
        void test_getPathFromAddFavoriteByParameters_toProduct_pageNull() {
            assertEquals(REDIRECT_TO_PRODUCT_WITH_PARAM + PRODUCT_ID, shopFacade.getPathFromAddFavoriteByParameters(PRODUCT_ID, PRODUCT_PAGE, PRODUCT_CATEGORY, null));
        }

        @Test
        void test_getPathFromAddFavoriteByParameters_toProduct_pageNotNull() {
            assertEquals(REDIRECT_TO_PRODUCT_WITH_PARAM + PRODUCT_ID + EXTENSION_PATH, shopFacade.getPathFromAddFavoriteByParameters(PRODUCT_ID, PRODUCT_PAGE, PRODUCT_CATEGORY, PAGE));
        }

        @Test
        void test_getPathFromAddFavoriteByParameters_toProducts_pageNull() {
            assertEquals(REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + PRODUCT_CATEGORY + AND_SIZE + PRODUCT_PAGE_SIZE, shopFacade.getPathFromAddFavoriteByParameters(PRODUCT_ID, LOCATION, PRODUCT_CATEGORY, null));
        }

        @Test
        void test_getPathFromAddFavoriteByParameters_toProducts_pageNotNull() {
            assertEquals(REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + PRODUCT_CATEGORY + AND_SIZE + PRODUCT_PAGE_SIZE + EXTENSION_PATH, shopFacade.getPathFromAddFavoriteByParameters(PRODUCT_ID, LOCATION, PRODUCT_CATEGORY, PAGE));
        }
    }

    @Test
    void test_getModelAndViewByParams_setViewName() {
        Long productId = PRODUCT_ID;
        String category = PRODUCT_CATEGORY;
        String currentLocation = SEARCH;
        Integer page = null;

        when(productService.getProductCategoryValue(any())).thenReturn(category);
        shopFacade.getPathFromAddFavoriteByParameters(productId, currentLocation, category, page);

        modelAndView = shopFacade.getModelAndViewByParams(productId, currentLocation, page);
        assertEquals(REDIRECT_TO_SEARCH_RESULT_SAVE, modelAndView.getViewName());
    }

    @Nested
    class PageByParam {

        @Test
        void test_getPageByParam_paramBuy() {
            SecurityContextHolder.setContext(securityContext);

            String param = BUY;

            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn(customUserDetail);
            when(customUserDetail.getUser()).thenReturn(userMock);

            shopFacade.getPageByParam(param, modelAndView);
            assertEquals(SUCCESS_BUY, modelAndView.getViewName());

            SecurityContextHolder.clearContext();
        }

        @Test
        void test_getPageByParam_paramNotBuy() {
            SecurityContextHolder.setContext(securityContext);

            String param = "any";

            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn(customUserDetail);
            when(customUserDetail.getUser()).thenReturn(userMock);

            shopFacade.getPageByParam(param, modelAndView);
            assertEquals(REDIRECT_TO_CART, modelAndView.getViewName());

            SecurityContextHolder.clearContext();
        }
    }

    @Test
    void test_getEshopView() {
        List<String> productCategories = List.of("tv", "phone");

        when(productCategoryService.getProductCategories()).thenReturn(productCategories);
        shopFacade.getEshopView(modelAndView);

        assertEquals(productCategories, modelAndView.getModel().get(PRODUCT_CATEGORIES));
        assertEquals(ESHOP, modelAndView.getViewName());
    }

    @Nested
    class UserEditForm {

        @Test
        void test_getUserEditForm_userIsPresent() {
            Long userId = 1L;
            user.setId(userId);
            user.setName("name");
            userFormDto.setId(userId);
            userFormDto.setName(user.getName());

            when(userService.getUserById(userId)).thenReturn(Optional.of(user));
            when(userMapper.convetrToUserFormDto(user)).thenReturn(userFormDto);
            shopFacade.getUserEditForm(userId, modelAndView);

            assertEquals(userFormDto, modelAndView.getModel().get(USER));
            assertEquals(EDIT, modelAndView.getViewName());
        }

        @Test
        void test_getUserEditForm_userIsNotPresent() {
            Long userId = 1L;

            when(userService.getUserById(userId)).thenReturn(Optional.empty());
            shopFacade.getUserEditForm(userId, modelAndView);

            assertEquals(ACCOUNT, modelAndView.getViewName());
        }
    }

//    @Disabled
    @Test
    void getAdminPage() {
        Long productOneId = 1L;
        Long productOneCount = 3L;
        Map<Long, Long> mostFavoriteOne = Map.of(productOneId, productOneCount);
        Long productTwoId = 2L;
        Long productTwoCount = 2L;
        Map<Long, Long> mostFavoriteTwo = Map.of(productTwoId, productTwoCount);
        List<Map<Long, Long>> mostFavorites = List.of(mostFavoriteOne, mostFavoriteTwo);
        ProductDto productDtoOne = getProductDto(productOneId);
        ProductDto productDtoTwo = getProductDto(productTwoId);
        List<Map<ProductDto, Long>> productsWithCount = List.of(Map.of(productDtoOne, productOneCount), Map.of(productDtoTwo, productTwoCount));

        when(cartService.getMostFavorite()).thenReturn(mostFavorites);
        when(productService.getProductDto(productOneId)).thenReturn(productDtoOne);
        when(productService.getProductDto(productTwoId)).thenReturn(productDtoTwo);
        shopFacade.getAdminPage(modelAndView);

        assertEquals(productsWithCount, modelAndView.getModel().get(PRODUCTS));
        //need your comment: PRODUCTS -> [{null=null},{null=null}] if
        // productWithCount.put(productService.getProductDto(mostFavorite.get(PRODUCT_ID)), mostFavorite.get(COUNT)); in stream
        //  where PRODUCT_ID and COUNT : productId and count
        // @Query("SELECT new map(product.id as productId, COUNT(product.id) as count) FROM Cart WHERE favorite = true GROUP BY product.id ORDER BY COUNT(product.id) DESC LIMIT 3")
        //    List<Map<Long, Long>> getMostFavorite();
        assertEquals(ADMIN_INFO, modelAndView.getViewName());
    }

    @Test
    void setPriceAndRedirectAttributes() {
    }

    private static ProductDto getProductDto(Long id) {
        return ProductDto.builder()
                         .id(id)
                         .build();
    }
}
