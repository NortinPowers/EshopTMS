package by.tms.eshop.exception;

import static by.tms.eshop.utils.Constants.ControllerMappingPath.ERROR_403;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_ERROR_500;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SOME_ERROR;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    public ModelAndView handleDataAccessException(DataAccessException ex) {
        log.error("DataAccess exception", ex);
        return new ModelAndView(REDIRECT_TO_ERROR_500);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessException(AccessDeniedException ex) {
        return ERROR_403;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest request, Exception ex) {
        log.error("Unexpected exception: " + request.getRequestURL(), ex);
        return new ModelAndView(REDIRECT_TO_SOME_ERROR);
    }
}
