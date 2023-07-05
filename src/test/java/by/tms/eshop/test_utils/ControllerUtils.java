package by.tms.eshop.test_utils;

import static by.tms.eshop.test_utils.Constants.MapperConstants.USER_ID;
import static by.tms.eshop.test_utils.Constants.MapperConstants.USER_LOGIN;
import static by.tms.eshop.test_utils.Constants.MapperConstants.USER_PASSWORD;
import static by.tms.eshop.test_utils.Constants.ROLE_ADMIN;
import static by.tms.eshop.test_utils.Constants.ROLE_USER;

import by.tms.eshop.domain.Role;
import by.tms.eshop.domain.User;
import by.tms.eshop.security.CustomUserDetail;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ControllerUtils {

    public static CustomUserDetail getCustomUserDetailRoleUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setLogin(USER_LOGIN);
        user.setPassword(USER_PASSWORD);
        user.setRole(Role.builder()
                         .role(ROLE_USER)
                         .build());
        return new CustomUserDetail(user);
    }

    public static CustomUserDetail getCustomUserDetailRoleAdmin() {
        User user = new User();
        user.setId(USER_ID);
        user.setLogin(USER_LOGIN);
        user.setPassword(USER_PASSWORD);
        user.setRole(Role.builder()
                         .role(ROLE_ADMIN)
                         .build());
        return new CustomUserDetail(user);
    }
}
