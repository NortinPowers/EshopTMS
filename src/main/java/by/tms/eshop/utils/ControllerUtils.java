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

    public static String getPathFromAddFavoriteByParameters(Long productId, String location, String productCategory, Integer page) {
        String path;
        if (Objects.equals(location, SEARCH)) {
            path = REDIRECT_TO_SEARCH_RESULT_SAVE;
        } else if (Objects.equals(location, PRODUCT_PAGE)) {
            path = REDIRECT_TO_PRODUCT_WITH_PARAM + productId;
        } else {
            path = REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + productCategory + AND_SIZE + SIZE_3;
        }
        return getPathByPagination(page, path);
    }

    public static String getPathByPagination(Integer page, String path) {
        if (page == null) {
            return path;
        } else {
            return path + AND_PAGE + page;
        }
    }
}
