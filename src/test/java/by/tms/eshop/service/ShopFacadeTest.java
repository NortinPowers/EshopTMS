package by.tms.eshop.service;

import static by.tms.eshop.test_utils.Constants.EXTENSION_PATH;
import static by.tms.eshop.test_utils.Constants.LOCATION;
import static by.tms.eshop.test_utils.Constants.PAGE;
import static by.tms.eshop.test_utils.Constants.PRODUCT_CATEGORY;
import static by.tms.eshop.test_utils.Constants.PRODUCT_ID;
import static by.tms.eshop.test_utils.Constants.ROLE_USER;
import static by.tms.eshop.test_utils.Constants.SECRET_QWERTY;
import static by.tms.eshop.utils.Constants.AND_SIZE;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_CART;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_FAVORITES;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCT_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_RESULT_SAVE;
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
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class ShopFacadeTest {

    @Autowired
    private ShopFacade shopFacade;

    @MockBean
    private ProductService productService;

    @MockBean
    private CartService cartService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private final String shopFlagElse = "someFlag";
    private final String shopFlagTrue = TRUE;
    private final User user = User.builder().build();

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
        UserFormDto userFormDto = UserFormDto.builder()
                                             .password("qwerty")
                                             .build();

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
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = Mockito.mock(Authentication.class);
        CustomUserDetail customUserDetail = Mockito.mock(CustomUserDetail.class);
        User userMock = Mockito.mock(User.class);

        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setName("testName");
        userFormDto.setSurname("testSurname");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(customUserDetail);
        when(customUserDetail.getUser()).thenReturn(userMock);
        shopFacade.editUser(userFormDto);

        verify(userMock).setName(userFormDto.getName());
        verify(userMock).setSurname(userFormDto.getSurname());
        verify(userService).addUser(userMock);

        SecurityContextHolder.clearContext();
    }
}
