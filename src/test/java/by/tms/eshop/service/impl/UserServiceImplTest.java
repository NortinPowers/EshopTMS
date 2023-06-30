package by.tms.eshop.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.tms.eshop.domain.User;
import by.tms.eshop.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;

    private final User user = User.builder()
                            .id(1L)
                            .login("testLogin")
                            .email("test@test.test")
                            .build();

    @Test
    void getUserByLogin() {
        String login = user.getLogin();

        when(userRepository.findUserByLogin(login)).thenReturn(Optional.of(user));
        Optional<User> userByLogin = userService.getUserByLogin(login);

        verify(userRepository, atLeastOnce()).findUserByLogin(login);
        assertEquals(user.getId(), userByLogin.get().getId());
    }

    @Test
    void addUser() {
        when(userRepository.saveAndFlush(user)).thenReturn(user);
        userService.addUser(user);

        verify(userRepository, atLeastOnce()).saveAndFlush(user);
    }

    @Test
    void getVerifyUser() {
    }

    @Test
    void getUserById() {
    }
}
