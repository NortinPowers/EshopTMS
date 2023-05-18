package by.tms.eshop.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static by.tms.eshop.utils.Constants.Attributes.START_TIME;
import static by.tms.eshop.utils.Constants.MAX_COMPLETION_TIME;

@Component
@Slf4j
public class TimeLoggerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME, startTime);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long startTime = (Long) request.getAttribute(START_TIME);
        long endTime = System.currentTimeMillis();
        long completionTime = endTime - startTime;
        if (completionTime > MAX_COMPLETION_TIME) {
            log.warn("Completion request time > " + completionTime + " for " + request.getRequestURL());
        }
    }
}