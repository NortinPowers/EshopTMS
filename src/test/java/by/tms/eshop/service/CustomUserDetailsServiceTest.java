package by.tms.eshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import by.tms.eshop.domain.User;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootTest
class CustomUserDetailsServiceTest {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private UserService userService;

    private final User user = User.builder()
                                  .login("testLogin")
                                  .build();

    @Test
    void test_loadUserByUsername_userIsPresent() {
        when(userService.getUserByLogin(user.getLogin())).thenReturn(Optional.of(user));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getLogin());

        assertEquals(userDetails.getUsername(), user.getLogin());
    }

    @Test
    void test_loadUserByUsername_userIsNotPresent() {
        when(userService.getUserByLogin(user.getLogin())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(any()));
    }
}
