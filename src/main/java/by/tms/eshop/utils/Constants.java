package by.tms.eshop.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String BUY = "buy";
    public static final String CONVERSATION = "conversation";
    public static final String SAVE = "save";
    public static final String ALL = "all";
    public static final String TRUE = "true";
    public static final Long MAX_COMPLETION_TIME = 2000L;
    public static final String AUTHORIZATION_PATTERN = "[a-zA-Z0-9]{4,30}";
    public static final String NAMES_PATTERN = "[a-zA-Z0-9]{3,30}";
    public static final String AND_PAGE = "&page=";
    public static final String AND_SIZE = "&size=";
    public static final String AND_SIZE_5 = "&size=5";
    public static final Integer SIZE_3 = 3;
    public static final String PAGE = "page";
    public static final String PRODUCT_ID = "productId";
    public static final String COUNT = "count";
    public static final String CATEGORY = "category";

    @UtilityClass
    public class MappingPath {

        public static final String ESHOP = "home/eshop";
        public static final String PRODUCTS = "product/products";
        public static final String PRODUCT = "product/product";
        public static final String LOGIN = "auth/login";
        public static final String EDIT = "auth/edit";
        public static final String CREATE_USER = "auth/create-user";
        public static final String SUCCESS_REGISTER = "auth/success-register";
        public static final String ACCOUNT = "account/account";
        public static final String ADMIN_INFO = "/admin/info";
        public static final String SEARCH_PATH = "search/search";
        public static final String SHOPPING_CART = "cart/shopping-cart";
        public static final String FAVORITES = "favorite/favorites";
        public static final String SUCCESS_BUY = "cart/success-buy";
        public static final String ERROR_500 = "error/error-500";
        public static final String ERROR_403 = "error/error-403";
        public static final String SOME_ERROR = "error/some-error";
        public static final String REDIRECT_TO_CART = "redirect:/cart";
        public static final String REDIRECT_TO_ADMIN = "redirect:/admin";
        public static final String REDIRECT_TO_ACCOUNT = "redirect:/account";
        public static final String REDIRECT_TO_ERROR_500 = "redirect:/error-500";
        public static final String REDIRECT_TO_SOME_ERROR = "redirect:/some-error";
        public static final String REDIRECT_TO_FAVORITES = "redirect:/favorites";
        public static final String REDIRECT_TO_SEARCH_RESULT_SAVE = "redirect:/search?result=save";
        public static final String REDIRECT_TO_SEARCH_FILTER_TRUE_RESULT_SAVE = "redirect:/search?filter=true&result=save";
        public static final String REDIRECT_TO_PRODUCT_WITH_PARAM = "redirect:/product/";
        public static final String REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM = "redirect:/products-page?category=";
    }

    @UtilityClass
    public class ControllerMappingPath {
        public static final String ESHOP = "/eshop";
        public static final String ROOT = "/";
        public static final String ADMIN = "/admin";
        public static final String SEARCH = "/search";
        public static final String SEARCH_FILTER = "/search-filter";
        public static final String SEARCH_PARAM = "/search-param";
        public static final String PRODUCTS_PAGE = "/products-page";
        public static final String ANY_PRODUCT = "/product/*";
        public static final String REGISTRATION = "/create-user";
        public static final String LOGIN = "/login";
        public static final String ERROR_403 = "/error-403";

    }

    @UtilityClass
    public class Attributes {

        public static final String USER = "user";
        public static final String USER_ORDER = "userOrder";
        public static final String FAVORITE_PRODUCTS = "favoriteProducts";
        public static final String PRODUCT = "product";
        public static final String PRODUCTS = "products";
        public static final String FOUND_PRODUCTS = "foundProducts";
        public static final String FILTER_FOUND_PRODUCTS = "filterFoundProducts";
        public static final String CART_PRODUCTS = "cartProducts";
        public static final String FULL_PRICE = "fullPrice";
        public static final String MODELS = "models";
        public static final String PRODUCT_CATEGORIES = "productCategories";
        public static final String START_TIME = "startTime";
        public static final String URL = "url";
        public static final String SUCCESS = "success";
        public static final String ERROR = "error";
    }

    @UtilityClass
    public class RequestParameters {

        public static final String FAVORITE = "favorite";
        public static final String SEARCH = "search";
        public static final String PRODUCT_PAGE = "product-page";
        public static final String ID = "id";
        public static final String SHOP = "shop";
        public static final String LOCATION = "location";
        public static final String SEARCH_CONDITION = "search-condition";
        public static final String FILTER = "filter";
        public static final String MIN_PRICE = "min-price";
        public static final String MAX_PRICE = "max-price";
        public static final String SELECT = "select";
    }

    @UtilityClass
    public class ErrorMessage {
        public static final String EXISTING_USER = "User with such a login has already been registered";
        public static final String EXISTING_EMAIL = "User with such an email has already been registered";
        public static final String AGE_LIMIT = "Registration is available from the age of 18";
        public static final String PASSWORDS_MATCHING = "The entered passwords do not match";
    }

    @UtilityClass
    public class UserVerifyField {
        public static final String LOGIN = "login";
        public static final String VERIFY_PASSWORD = "verifyPassword";
        public static final String EMAIL = "email";
    }
}
