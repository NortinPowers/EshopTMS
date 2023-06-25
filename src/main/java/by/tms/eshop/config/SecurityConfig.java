package by.tms.eshop.config;

import static by.tms.eshop.utils.Constants.ControllerMappingPath.ADMIN;
import static by.tms.eshop.utils.Constants.ControllerMappingPath.ANY_PRODUCT;
import static by.tms.eshop.utils.Constants.ControllerMappingPath.ESHOP;
import static by.tms.eshop.utils.Constants.ControllerMappingPath.LOGIN;
import static by.tms.eshop.utils.Constants.ControllerMappingPath.PRODUCTS_PAGE;
import static by.tms.eshop.utils.Constants.ControllerMappingPath.REGISTRATION;
import static by.tms.eshop.utils.Constants.ControllerMappingPath.ROOT;
import static by.tms.eshop.utils.Constants.ControllerMappingPath.SEARCH;
import static by.tms.eshop.utils.Constants.ControllerMappingPath.SEARCH_FILTER;
import static by.tms.eshop.utils.Constants.ControllerMappingPath.SEARCH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.SUCCESS_REGISTER;

import by.tms.eshop.exception.GlobalExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomLogoutHandler customLogoutHandler;
    private final GlobalExceptionHandler globalExceptionHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(ADMIN)
                        .hasRole("ADMIN")
                        .requestMatchers(ROOT, ESHOP, SEARCH, SEARCH_FILTER, SEARCH_PARAM, PRODUCTS_PAGE, ANY_PRODUCT, REGISTRATION, SUCCESS_REGISTER)
                        .permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .exceptionHandling(exceptionHandler -> exceptionHandler
                        .accessDeniedHandler(new CustomAccessDeniedHandler(globalExceptionHandler))
                )
                .formLogin(form -> form
                        .loginPage(LOGIN)
                        .successHandler(customAuthenticationSuccessHandler)
                        .permitAll()
                )
                .rememberMe(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutSuccessHandler(customLogoutHandler)
                        .permitAll())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
