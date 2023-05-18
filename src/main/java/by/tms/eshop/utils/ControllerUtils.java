package by.tms.eshop.utils;

import by.tms.eshop.domain.User;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.dto.UserDto;
import by.tms.eshop.utils.Constants.UserVerifyField;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static by.tms.eshop.utils.Constants.ALL;
import static by.tms.eshop.utils.Constants.Attributes.FILTER_FOUND_PRODUCTS;
import static by.tms.eshop.utils.Constants.Attributes.FOUND_PRODUCTS;
import static by.tms.eshop.utils.Constants.Attributes.USER_ACCESS_PERMISSION;
import static by.tms.eshop.utils.Constants.Attributes.USER_UUID;
import static by.tms.eshop.utils.Constants.CONVERSATION;
import static by.tms.eshop.utils.Constants.MappingPath.ESHOP;
import static by.tms.eshop.utils.Constants.MappingPath.LOGIN;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCT_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_RESULT_SAVE;
import static by.tms.eshop.utils.Constants.RequestParameters.FILTER;
import static by.tms.eshop.utils.Constants.RequestParameters.PRODUCT_PAGE;
import static by.tms.eshop.utils.Constants.RequestParameters.SEARCH;
import static by.tms.eshop.utils.Constants.SAVE;
import static by.tms.eshop.utils.Constants.TRUE;
import static by.tms.eshop.utils.Constants.UserVerifyField.BIRTHDAY;
import static by.tms.eshop.utils.Constants.UserVerifyField.EMAIL;
import static by.tms.eshop.utils.Constants.UserVerifyField.NAME;
import static by.tms.eshop.utils.Constants.UserVerifyField.PASSWORD;
import static by.tms.eshop.utils.Constants.UserVerifyField.SURNAME;
import static by.tms.eshop.utils.Constants.UserVerifyField.VERIFY_PASSWORD;
import static java.util.UUID.randomUUID;

@Slf4j
@UtilityClass
public class ControllerUtils {

    public static boolean isVerifyUser(User user, String password) {
        return user.getPassword().equals(password);
    }

    public static void saveUserSession(HttpServletRequest req, UserDto userDto) {
        HttpSession session = req.getSession();
        session.setAttribute(USER_ACCESS_PERMISSION, userDto);
        log.info("The user with a login " + userDto.getLogin() + " is logged in");
        String userUUID = randomUUID().toString();
        MDC.put(CONVERSATION, userUUID);
        session.setAttribute(USER_UUID, userUUID);
        log.info("User with the login " + userDto.getLogin() + " has been assigned a UUID");
    }

    public static String createOrderNumber(Long id) {
        String uuid = randomUUID().toString();
        return "#" + id + "-" + uuid;
    }

    public static Long getUserId(HttpSession session) {
        return ((UserDto) session.getAttribute(USER_ACCESS_PERMISSION)).getId();
    }

    public static BigDecimal getPrice(HttpServletRequest request, String param, BigDecimal defaultValue) {
        String value = request.getParameter(param);
        return StringUtils.isNotBlank(value) ? new BigDecimal(value) : defaultValue;
    }

    public static Set<ProductDto> applyPriceFilterOnProducts(BigDecimal minPrice, BigDecimal maxPrice, Set<ProductDto> products) {
        products = products.stream()
                .filter(product -> product.getPrice().compareTo(minPrice) > 0 && product.getPrice().compareTo(maxPrice) < 0)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return products;
    }

    public static Set<ProductDto> applyTypeFilterOnProducts(String type, Set<ProductDto> products) {
        if (!ALL.equals(type)) {
            products = products.stream()
                    .filter(product -> product.getCategory().equals(type))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }
        return products;
    }

    public static String getPathFromAddFavoriteByParameters(Long productId, String location, String productType) {
        String path;
        if (Objects.equals(location, SEARCH)) {
            path = REDIRECT_TO_SEARCH_RESULT_SAVE;
        } else if (Objects.equals(location, PRODUCT_PAGE)) {
            path = REDIRECT_TO_PRODUCT_WITH_PARAM + productId;
        } else {
            path = REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + productType;
        }
        return path;
    }

    public static void fillError(String field, ModelAndView modelAndView, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors(field)) {
            modelAndView.addObject(field + "Error", Objects.requireNonNull(bindingResult.getFieldError(field))
                    .getDefaultMessage());
        }
    }

    public static void closeUserSession(HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute(USER_ACCESS_PERMISSION);
        String userUUID = (String) session.getAttribute(USER_UUID);
        log.info("User [" + userUUID + "] with a login " + userDto.getLogin() + " logged out of the system");
        session.removeAttribute(USER_ACCESS_PERMISSION);
        session.removeAttribute(USER_UUID);
        session.invalidate();
    }

    public static void setFilterAttribute(HttpSession session, String filter) {
        if (TRUE.equals(filter)) {
            session.setAttribute(FILTER, new Object());
        }
    }

    public static void removeUnsavedAttribute(HttpSession session, String filterFlag) {
        if (!SAVE.equals(filterFlag)) {
            session.removeAttribute(FOUND_PRODUCTS);
            session.removeAttribute(FILTER_FOUND_PRODUCTS);
        }
    }

    public static void fillUserValidationError(BindingResult bindingResult, ModelAndView modelAndView) {
        fillError(UserVerifyField.LOGIN, modelAndView, bindingResult);
        fillError(PASSWORD, modelAndView, bindingResult);
        fillError(VERIFY_PASSWORD, modelAndView, bindingResult);
        fillError(NAME, modelAndView, bindingResult);
        fillError(SURNAME, modelAndView, bindingResult);
        fillError(EMAIL, modelAndView, bindingResult);
        fillError(BIRTHDAY, modelAndView, bindingResult);
    }

    public static void fillsLoginVerifyErrors(BindingResult bindingResult, ModelAndView modelAndView) {
        fillError(UserVerifyField.LOGIN, modelAndView, bindingResult);
        fillError(PASSWORD, modelAndView, bindingResult);
    }

    public static void setViewByAccessPermission(HttpSession session, ModelAndView modelAndView) {
        if (session.getAttribute(USER_ACCESS_PERMISSION) != null) {
            modelAndView.setViewName(ESHOP);
        } else {
            modelAndView.setViewName(LOGIN);
        }
    }
}