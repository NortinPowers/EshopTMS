package by.tms.eshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//@RequiredArgsConstructor
public class SecurityConfig {

//    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
//                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
//                        .permitAll()
                        .requestMatchers("/", "/eshop", "/search", "/search-filter", "/search-param", "/products-page", "/product/*", "/create-user")
                        .permitAll()
//                        .requestMatchers("/error-500", "/some-error")
//                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
//                        .usernameParameter("login")
//                        .successForwardUrl("/eshop")
//                        .failureForwardUrl("/login")
                        .permitAll()
                )
//                .rememberMe(Customizer.withDefaults())
                .logout((logout) -> logout
                        .clearAuthentication(true)
                        .deleteCookies()
                        .permitAll());
//                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
//    { return authenticationConfiguration.getAuthenticationManager();}

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return customUserDetailsService;
//    }

        @Bean
    public UserDetailsService userDetailsService() {
            UserDetails user =
                User.withDefaultPasswordEncoder()
                    .username("q")
                    .password("q")
                    .roles("USER")
                    .build();
        return new InMemoryUserDetailsManager(user);
    }


//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/", "/home").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                .logout((logout) -> logout.permitAll());
//
//        return http.build();
//    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                    .username("user")
//                    .password("password")
//                    .roles("USER")
//                    .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
}
