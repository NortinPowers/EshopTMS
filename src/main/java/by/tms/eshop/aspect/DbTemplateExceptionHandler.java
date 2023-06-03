package by.tms.eshop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class DbTemplateExceptionHandler {

    @AfterThrowing(pointcut = "execution(* by.tms.eshop.repository.*.*(..))", throwing = "ex")
    public void logDbException(Exception ex) {
        log.error("Unexpected error", ex);
    }
}
