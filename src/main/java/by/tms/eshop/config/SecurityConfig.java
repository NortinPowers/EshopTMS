package by.tms.eshop.config;

import static by.tms.eshop.utils.Constants.MappingPath.SUCCESS_REGISTER;

import by.tms.eshop.service.CustomUserDetailsService;
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

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final LogoutHandler logoutHandler;

    @SuppressWarnings({"checkstyle:MultipleStringLiterals", "checkstyle:UnnecessaryParentheses"})
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        SecurityContextRepository repo = new HttpSessionSecurityContextRepository();
        return http
//                .userDetailsService(customUserDetailsService)

//                .sessionManagement((session) -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
//                .securityContext((context) -> context
//                        .securityContextRepository(repo)
//                )
//                .sessionManagement((session) -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests
                                               .requestMatchers("/", "/eshop", "/search", "/search-filter", "/search-param", "/products-page", "/product/*", "/create-user", SUCCESS_REGISTER)
                                               .permitAll()
                                               .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                               .permitAll()
//                        .requestMatchers("/error-500", "/some-error")
//                        .permitAll()
                                               .anyRequest()
                                               .authenticated()
                )
                .formLogin(form -> form
                                   .loginPage("/login")
//                                   .loginProcessingUrl("/login")
//                        .usernameParameter("login")
                                   .successHandler(customAuthenticationSuccessHandler)
//                                   .successForwardUrl("/")
//                        .failureForwardUrl("/login")
                                   .permitAll()
                )
                .rememberMe(Customizer.withDefaults())
                .logout((logout) -> logout
//                        .logoutSuccessUrl("/")
                        .logoutSuccessHandler(logoutHandler)
//                        .clearAuthentication(true)
//                        .deleteCookies()
.permitAll())
                .build();
//                .logout(LogoutConfigurer::permitAll);
//        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

//    @EventListener
//    public void setupSecurityContext(ContextRefreshedEvent event) {
//        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
//        SecurityContextHolder.getContext().setAuthentication(new SystemAuthentication());
//    }
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return customUserDetailsService;

//    }
//        @Bean
//    public UserDetailsService userDetailsService() {
//            UserDetails user =
//                User.withDefaultPasswordEncoder()
//                    .username("q")
//                    .password("q")
//                    .roles("USER")
//                    .build();
//        return new InMemoryUserDetailsManager(user);
}
