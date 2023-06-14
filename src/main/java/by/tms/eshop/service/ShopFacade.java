package by.tms.eshop.service;

import static by.tms.eshop.utils.Constants.ALL;
import static by.tms.eshop.utils.Constants.Attributes.FILTER_FOUND_PRODUCTS;
import static by.tms.eshop.utils.Constants.Attributes.FOUND_PRODUCTS;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_CART;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_FAVORITES;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCT_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_FILTER_TRUE_RESULT_SAVE;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_RESULT_SAVE;
import static by.tms.eshop.utils.Constants.RequestParameters.FAVORITE;
import static by.tms.eshop.utils.Constants.RequestParameters.MAX_PRICE;
import static by.tms.eshop.utils.Constants.RequestParameters.MIN_PRICE;
import static by.tms.eshop.utils.Constants.RequestParameters.PRODUCT_PAGE;
import static by.tms.eshop.utils.Constants.RequestParameters.SEARCH;
import static by.tms.eshop.utils.Constants.RequestParameters.TRUE;
import static by.tms.eshop.utils.ControllerUtils.applyPriceFilterOnProducts;
import static by.tms.eshop.utils.ControllerUtils.applyTypeFilterOnProducts;
import static by.tms.eshop.utils.ControllerUtils.getPrice;

import by.tms.eshop.domain.User;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.dto.UserFormDto;
import by.tms.eshop.dto.conversion.Converter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class ShopFacade {

    private final CartService cartService;
    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;
    private final Converter converter;
    private final PasswordEncoder passwordEncoder;

    public void carriesPurchase(Long userId) {
        List<ProductDto> productsDto = cartService.getPurchasedProducts(userId, converter.selectCart());
        orderService.saveUserOrder(userId, productsDto);
        //--
        cartService.deleteCartProductsAfterBuy(userId);
    }

    //    public String getPathFromAddCartByParameters(Long productId, String shopFlag, String location) {
    public String getPathFromAddCartByParameters(Long productId, String shopFlag, String location, Integer page) {
        String path;
        if (Objects.equals(shopFlag, TRUE)) {
            path = REDIRECT_TO_CART;
        } else if (Objects.equals(location, FAVORITE)) {
            path = REDIRECT_TO_FAVORITES;
        } else if (Objects.equals(location, SEARCH)) {
            path = REDIRECT_TO_SEARCH_RESULT_SAVE;
        } else if (Objects.equals(location, PRODUCT_PAGE)) {
            path = REDIRECT_TO_PRODUCT_WITH_PARAM + productId;
        } else {
            String productCategory = productService.getProductCategoryValue(productId);
            path = REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + productCategory + "&size=3";
        }
        if (page == null) {
//        if (page<0) {
            return path;
        } else {
            return path + "&page=" + page;
        }
//        return path;
    }

    public ModelAndView getSearchFilterResultPagePath(HttpServletRequest request, String category) {
        BigDecimal minPrice = getPrice(request, MIN_PRICE, BigDecimal.ZERO);
        BigDecimal maxPrice = getPrice(request, MAX_PRICE, new BigDecimal(Long.MAX_VALUE));
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession(false);
        if (session.getAttribute(FOUND_PRODUCTS) != null) {
            session.setAttribute(FILTER_FOUND_PRODUCTS, getProductByFilter(session, category, minPrice, maxPrice));
            modelAndView.setViewName(REDIRECT_TO_SEARCH_FILTER_TRUE_RESULT_SAVE);
        } else {
            if (!ALL.equals(category)) {
                session.setAttribute(FOUND_PRODUCTS, productService.selectProductsFromCategoryByFilter(category, minPrice, maxPrice));
            } else {
                session.setAttribute(FOUND_PRODUCTS, productService.selectAllProductsByFilter(minPrice, maxPrice));
            }
            modelAndView.setViewName(REDIRECT_TO_SEARCH_RESULT_SAVE);
        }
        return modelAndView;
    }

    public void returnProductsBySearchCondition(HttpSession session, String searchCondition) {
        if (!searchCondition.isEmpty()) {
            Set<ProductDto> products = productService.getFoundedProducts(searchCondition);
            session.setAttribute(FOUND_PRODUCTS, products);
        }
    }

//    public void createAndLoginUser(HttpServletRequest request, UserFormDto user) {
//        User userEntity = convertor.makeUserModelTransfer(user);
//        userService.addUser(userEntity);
//        saveUserSession(request, convertor.makeUserDtoModelTransfer(userEntity));
//    }

    public void createUser(UserFormDto user) {
//    public void createUser(HttpServletRequest request, UserFormDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userEntity = converter.makeUserModelTransfer(user);
        userService.addUser(userEntity);
//        markUserToLog(converter.makeUserDtoModelTransfer(userEntity));
//        markUser(request, converter.makeUserDtoModelTransfer(userEntity));
    }

//    public void checkLoginUser(HttpServletRequest request, UserFormDto user, ModelAndView modelAndView) {
//        Optional<User> incomingUser = userService.getUserByLogin(user.getLogin());
//        if (incomingUser.isPresent() && isVerifyUser(incomingUser.get(), user.getPassword())) {
//            UserDto userDto = converter.makeUserDtoModelTransfer(incomingUser.get());
//            saveUserSession(request, userDto);
//            modelAndView.setViewName(REDIRECT_TO_ESHOP);
//        } else {
//            modelAndView.addObject(LOGIN_ERROR, RECHECK_DATA);
//            modelAndView.setViewName(LOGIN);
//        }
//    }

//    public void saveUserOldStyle(HttpServletRequest request, User user, ModelAndView modelAndView) {
//        UserDto userDto = convertor.makeUserDtoModelTransfer(user);
//        saveUserSession(request, userDto);
//        modelAndView.setViewName(REDIRECT_TO_ESHOP);
//    }

    private Set<ProductDto> getProductByFilter(HttpSession session, String type, BigDecimal minPrice, BigDecimal maxPrice) {
        Set<ProductDto> products;
        products = (Set<ProductDto>) session.getAttribute(FOUND_PRODUCTS);
        products = applyPriceFilterOnProducts(minPrice, maxPrice, products);
        products = applyTypeFilterOnProducts(type, products);
        return products;
    }
}
