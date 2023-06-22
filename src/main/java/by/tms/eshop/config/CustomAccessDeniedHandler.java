package by.tms.eshop.config;

import by.tms.eshop.exception.GlobalExceptionHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.servlet.View;

@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final GlobalExceptionHandler globalExceptionHandler;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException, ServletException {
        String path = globalExceptionHandler.handleAccessException(ex);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FORBIDDEN);
        request.setAttribute("Access denied", ex);
        response.sendRedirect(path);
    }
}
