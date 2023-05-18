package by.tms.eshop.service;

import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.dto.UserDto;
import by.tms.eshop.dto.UserFormDto;
import by.tms.eshop.domain.User;
import by.tms.eshop.utils.DtoUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static by.tms.eshop.utils.Constants.Attributes.FILTER_FOUND_PRODUCTS;
import static by.tms.eshop.utils.Constants.Attributes.FOUND_PRODUCTS;
import static by.tms.eshop.utils.Constants.Attributes.LOGIN_ERROR;
import static by.tms.eshop.utils.Constants.ErrorMessage.RECHECK_DATA;
import static by.tms.eshop.utils.Constants.MappingPath.LOGIN;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_CART;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_ESHOP;
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
import static by.tms.eshop.utils.ControllerUtils.getPrice;
import static by.tms.eshop.utils.ControllerUtils.isVerifyUser;
import static by.tms.eshop.utils.ControllerUtils.saveUserSession;
import static by.tms.eshop.utils.DtoUtils.makeUserDtoModelTransfer;
import static by.tms.eshop.utils.DtoUtils.makeUserModelTransfer;
import static by.tms.eshop.utils.ServiceUtils.getProductByFilter;

@Component
@RequiredArgsConstructor
public class ShopFacade {

    private final CartService cartService;
    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;

    public void carriesPurchase(Long userId) {
        List<ProductDto> productsDto = cartService.getPurchasedProducts(userId, DtoUtils.selectCart());
        orderService.saveUserOrder(userId, productsDto);
        cartService.deleteCartProductsAfterBuy(userId);
    }

    public String getPathFromAddCartByParameters(Long productId, String shopFlag, String location) {
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
            path = REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + productCategory;
        }
        return path;
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
            session.setAttribute(FOUND_PRODUCTS, productService.selectAllProductsByFilter(category, minPrice, maxPrice));
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

    public void createAndLoginUser(HttpServletRequest request, UserFormDto user) {
        User userEntity = makeUserModelTransfer(user);
        userService.addUser(userEntity);
        saveUserSession(request, makeUserDtoModelTransfer(userEntity));
    }

    public void checkLoginUser(HttpServletRequest request, UserFormDto user, ModelAndView modelAndView) {
        Optional<User> incomingUser = userService.getUserByLogin(user.getLogin());
        if (incomingUser.isPresent() && isVerifyUser(incomingUser.get(), user.getPassword())) {
            UserDto userDto = makeUserDtoModelTransfer(incomingUser.get());
            saveUserSession(request, userDto);
            modelAndView.setViewName(REDIRECT_TO_ESHOP);
        } else {
            modelAndView.addObject(LOGIN_ERROR, RECHECK_DATA);
            modelAndView.setViewName(LOGIN);
        }
    }
}