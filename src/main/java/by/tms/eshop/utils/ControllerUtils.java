package by.tms.eshop.utils;

import static by.tms.eshop.utils.Constants.AND_PAGE;
import static by.tms.eshop.utils.Constants.AND_SIZE;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCT_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_RESULT_SAVE;
import static by.tms.eshop.utils.Constants.RequestParameters.PRODUCT_PAGE;
import static by.tms.eshop.utils.Constants.RequestParameters.SEARCH;
import static by.tms.eshop.utils.Constants.SIZE_3;

import by.tms.eshop.domain.User;
import by.tms.eshop.dto.CartDto;
import by.tms.eshop.security.CustomUserDetail;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@UtilityClass
public class ControllerUtils {

//    public static boolean isVerifyUser(User user, String password) {
//        return user.getPassword().equals(password);
//    }

//    public static void saveUserSession(HttpServletRequest req, UserDto userDto) {
//        HttpSession session = req.getSession();
//        session.setAttribute(USER_ACCESS_PERMISSION, userDto);
//        log.info("The user with a login " + userDto.getLogin() + " is logged in");
//        String userUuid = randomUUID().toString();
//        MDC.put(CONVERSATION, userUuid);
//        session.setAttribute(USER_UUID, userUuid);
//        log.info("User with the login " + userDto.getLogin() + " has been assigned a UUID");
//    }

//        public static void markUserToLog(UserDto userDto) {
//    public static void markUserToLog(User user) {
////    public static void markUser(HttpServletRequest req, UserDto userDto) {
////        HttpSession session = req.getSession();
////        session.setAttribute(USER_ACCESS_PERMISSION, userDto);
//        log.info("The user with a login " + user.getLogin() + " is logged in");
////        log.info("The user with a login " + userDto.getLogin() + " is logged in");
////        String userUuid = randomUUID().toString();
////        MDC.put(CONVERSATION, userUuid);
//        MDC.put(CONVERSATION, randomUUID().toString());
////        session.setAttribute(USER_UUID, userUuid);
//        log.info("User with the login " + user.getLogin() + " has been assigned a UUID");
////        log.info("User with the login " + userDto.getLogin() + " has been assigned a UUID");
//    }

//    public static String createOrderNumber(Long id) {
//        String uuid = randomUUID().toString();
//        return "#" + id + "-" + uuid;
//    }

//    public static Long getUserId(HttpSession session) {
//        return getUserDto(session).getId();
//    }

//    public static BigDecimal getPrice(HttpServletRequest request, String param, BigDecimal defaultValue) {
//        String value = request.getParameter(param);
//        return StringUtils.isNotBlank(value) ? new BigDecimal(value) : defaultValue;
//    }
//
//    @SuppressWarnings("checkstyle:ParameterAssignment")
//    public static Set<ProductDto> applyPriceFilterOnProducts(BigDecimal minPrice, BigDecimal maxPrice, Set<ProductDto> products) {
//        products = products.stream()
//                           .filter(product -> product.getPrice().compareTo(minPrice) > 0 && product.getPrice().compareTo(maxPrice) < 0)
//                           .collect(Collectors.toCollection(LinkedHashSet::new));
//        return products;
//    }
//
//    @SuppressWarnings("checkstyle:ParameterAssignment")
//    public static Set<ProductDto> applyTypeFilterOnProducts(String type, Set<ProductDto> products) {
//        if (!ALL.equals(type)) {
//            products = products.stream()
//                               .filter(product -> product.getCategory().equals(type))
//                               .collect(Collectors.toCollection(LinkedHashSet::new));
//        }
//        return products;
//    }

    public static String getPathFromAddFavoriteByParameters(Long productId, String location, String productCategory, Integer page) {
//    public static String getPathFromAddFavoriteByParameters(Long productId, String location, String productCategory) {
        String path;
        if (Objects.equals(location, SEARCH)) {
            path = REDIRECT_TO_SEARCH_RESULT_SAVE;
        } else if (Objects.equals(location, PRODUCT_PAGE)) {
            path = REDIRECT_TO_PRODUCT_WITH_PARAM + productId;
        } else {
            path = REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + productCategory + AND_SIZE + SIZE_3;
//            path = REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + productCategory + "&size=3";
        }
        if (page == null) {
//        if (page < 0) {
            return path;
        } else {
            return path + AND_PAGE + page;
//            return path + "&page=" + page;
        }
//        return path;
    }

//    public static void fillError(String field, ModelAndView modelAndView, BindingResult bindingResult) {
//        if (bindingResult.hasFieldErrors(field)) {
//            FieldError fieldError = bindingResult.getFieldError(field);
//            if (fieldError != null) {
//                modelAndView.addObject(field + "Error", fieldError.getDefaultMessage());
//            }
//        }
//    }

//    public static void closeUserSession(HttpSession session) {
//        UserDto userDto = getUserDto(session);
//        String userUuid = (String) session.getAttribute(USER_UUID);
//        log.info("User [" + userUuid + "] with a login " + userDto.getLogin() + " logged out of the system");
//        session.removeAttribute(USER_ACCESS_PERMISSION);
//        session.removeAttribute(USER_UUID);
//        session.invalidate();
//    }

//    //    public static void writeLoggedToLog(HttpSession session) {
//    public static void writeLoggedToLog() {
////        UserDto userDto = getUserDto(session);
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        CustomUserDetail principal = (CustomUserDetail) authentication.getPrincipal();
////        String login = principal.getUser().getLogin();
////        String userUuid = (String) session.getAttribute(USER_UUID);
//        log.info("User with a login " + getAuthenticationUser().getLogin() + " logged out of the system");
////        log.info("User [" + userUuid + "] with a login " + userDto.getLogin() + " logged out of the system");
////        session.removeAttribute(USER_ACCESS_PERMISSION);
////        session.removeAttribute(USER_UUID);
////        ??
////        session.invalidate();
//    }

//    public static void setFilterAttribute(HttpSession session, String filter) {
//        if (TRUE.equals(filter)) {
//            session.setAttribute(FILTER, new Object());
//        }
//    }
//
//    public static void removeUnsavedAttribute(HttpSession session, String filterFlag) {
//        if (!SAVE.equals(filterFlag)) {
//            session.removeAttribute(FOUND_PRODUCTS);
//            session.removeAttribute(FILTER_FOUND_PRODUCTS);
//        }
//    }

//    public static void fillUserValidationError(BindingResult bindingResult, ModelAndView modelAndView) {
//        fillError(UserVerifyField.LOGIN, modelAndView, bindingResult);
//        fillError(PASSWORD, modelAndView, bindingResult);
//        fillError(VERIFY_PASSWORD, modelAndView, bindingResult);
//        fillError(NAME, modelAndView, bindingResult);
//        fillError(SURNAME, modelAndView, bindingResult);
//        fillError(EMAIL, modelAndView, bindingResult);
//        fillError(BIRTHDAY, modelAndView, bindingResult);
//    }

//    public static void fillsLoginVerifyErrors(BindingResult bindingResult, ModelAndView modelAndView) {
//        fillError(UserVerifyField.LOGIN, modelAndView, bindingResult);
//        fillError(PASSWORD, modelAndView, bindingResult);
//    }

//    public static void fillsEditVerifyErrors(BindingResult bindingResult, ModelAndView modelAndView) {
//        fillError(PASSWORD, modelAndView, bindingResult);
//        fillError(VERIFY_PASSWORD, modelAndView, bindingResult);
//        fillError(NAME, modelAndView, bindingResult);
//        fillError(SURNAME, modelAndView, bindingResult);
//        fillError(BIRTHDAY, modelAndView, bindingResult);
//    }

//    public static void setViewByAccessPermission(HttpSession session, ModelAndView modelAndView) {
//        if (getUserDto(session) != null) {
//            modelAndView.setViewName(ESHOP);
//        } else {
//            modelAndView.setViewName(LOGIN);
//        }
//    }

//    public static UserDto getUserDto(HttpSession session) {
//        return (UserDto) session.getAttribute(USER_ACCESS_PERMISSION);
//    }

//    public static BigDecimal getProductsPrice(List<ImmutablePair<ProductDto, Integer>> productWithCount) {
//        BigDecimal fullPrice = BigDecimal.ZERO;
//        for (ImmutablePair<ProductDto, Integer> product : productWithCount) {
//            BigDecimal totalPrice = product.getLeft().getPrice().multiply(new BigDecimal(product.getRight()));
//            fullPrice = fullPrice.add(totalPrice);
//        }
//        return fullPrice;
//    }

    public static BigDecimal getProductsPrice(List<CartDto> carts) {
        BigDecimal fullPrice = BigDecimal.ZERO;
        for (CartDto cart : carts) {
            BigDecimal totalPrice = cart.getProductDto().getPrice().multiply(new BigDecimal(cart.getCount()));
            fullPrice = fullPrice.add(totalPrice);
        }
        return fullPrice;
    }

    public static User getAuthenticationUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail principal = (CustomUserDetail) authentication.getPrincipal();
        return principal.getUser();
    }

    public static Long getAuthenticationUserId() {
        return getAuthenticationUser().getId();
    }
}
