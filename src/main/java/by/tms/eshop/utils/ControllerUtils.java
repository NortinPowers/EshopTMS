package by.tms.eshop.utils;

import static by.tms.eshop.utils.Constants.AND_PAGE;

import by.tms.eshop.domain.User;
import by.tms.eshop.dto.CartDto;
import by.tms.eshop.security.CustomUserDetail;
import java.math.BigDecimal;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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

    public static String getPathByPagination(Integer page, String path) {
        if (page == null) {
            return path;
        } else {
            return path + AND_PAGE + page;
        }
    }
}
