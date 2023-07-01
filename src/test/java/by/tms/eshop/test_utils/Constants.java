package by.tms.eshop.test_utils;

import static by.tms.eshop.utils.Constants.AND_PAGE;

import by.tms.eshop.domain.Product;
import by.tms.eshop.domain.ProductCategory;
import by.tms.eshop.domain.Role;
import by.tms.eshop.domain.User;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.dto.RoleDto;
import by.tms.eshop.dto.UserDto;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final Integer PAGE = 2;
    public static final Long PRODUCT_ID = 1L;
    public static final String EXTENSION_PATH = AND_PAGE + PAGE;
    public static final String LOCATION = "someLocation";
    public static final String PRODUCT_CATEGORY = "someCategory";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String SECRET_QWERTY = "secret qwerty";
    public static final String TV = "tv";
    public static final String PHONE = "phone";
    public static final String TEST_PROPERTY_SOURCE_LOCATIONS = "classpath:application-test.properties";

    @UtilityClass
    public class MapperConstants {
        public static final Long ROLE_ID = 1L;
        public static final String USER_ROLE = "role";
        public static final Role ROLE = Role.builder()
                                             .id(ROLE_ID)
                                             .role(USER_ROLE)
                                             .build();
        public static final RoleDto ROLE_DTO = RoleDto.builder()
                                                .id(ROLE_ID)
                                                .role(USER_ROLE)
                                                .build();
        public static final Long USER_ID = 1L;
        public static final String USER_LOGIN = "login";
        public static final String USER_PASSWORD = "password";
        public static final String USER_NAME = "name";
        public static final String USER_SURNAME = "surname";
        public static final String USER_EMAIL = "test@email.com";
        public static final LocalDate USER_BIRTHDAY = LocalDate.of(2000, 1, 1);
        public static final User USER = User.builder()
                                             .id(USER_ID)
                                             .login(USER_LOGIN)
                                             .password(USER_PASSWORD)
                                             .name(USER_NAME)
                                             .surname(USER_SURNAME)
                                             .email(USER_EMAIL)
                                             .birthday(USER_BIRTHDAY)
                                             .role(ROLE)
                                             .build();
        public static final UserDto USER_DTO = UserDto.builder()
                                                      .id(USER_ID)
                                                      .login(USER_LOGIN)
                                                      .name(USER_NAME)
                                                      .surname(USER_SURNAME)
                                                      .email(USER_EMAIL)
                                                      .birthday(USER_BIRTHDAY)
                                                      .build();
        public static final Long PRODUCT_ID = 1L;
        public static final String PRODUCT_NAME = "productName";
        public static final BigDecimal PRODUCT_PRICE = BigDecimal.valueOf(300);
        public static final Long CATEGORY_ID = 2L;
        public static final String CATEGORY_CATEGORY = "someCategory";
        public static final ProductCategory PRODUCT_CATEGORY = ProductCategory.builder()
                                                                        .id(CATEGORY_ID)
                                                                        .category(CATEGORY_CATEGORY)
                                                                        .build();
        public static final String PRODUCT_INFO = "productInfo";
        public static final Product PRODUCT = Product.builder()
                                               .id(PRODUCT_ID)
                                               .name(PRODUCT_NAME)
                                               .price(PRODUCT_PRICE)
                                               .productCategory(PRODUCT_CATEGORY)
                                               .info(PRODUCT_INFO)
                                               .build();
        public static final ProductDto PRODUCT_DTO = ProductDto.builder()
                                                         .id(PRODUCT_ID)
                                                         .name(PRODUCT_NAME)
                                                         .price(PRODUCT_PRICE)
                                                         .category(CATEGORY_CATEGORY)
                                                         .info(PRODUCT_INFO)
                                                         .build();
    }
}
