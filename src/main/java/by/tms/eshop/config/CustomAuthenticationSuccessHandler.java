package by.tms.eshop.config;

import static by.tms.eshop.utils.Constants.CONVERSATION;
import static by.tms.eshop.utils.Constants.ControllerMappingPath.ESHOP;

import by.tms.eshop.security.CustomUserDetail;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String userUuid = UUID.randomUUID().toString();
        MDC.put(CONVERSATION, userUuid);
        CustomUserDetail principal = (CustomUserDetail) authentication.getPrincipal();
        log.info("The user with a login " + principal.getUser().getLogin() + " is logged in, has been assigned a UUID [" + userUuid + "]");
        response.sendRedirect(ESHOP);
    }
}
