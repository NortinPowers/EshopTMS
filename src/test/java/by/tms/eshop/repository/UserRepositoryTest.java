package by.tms.eshop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import by.tms.eshop.domain.User;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class UserRepositoryTest {

    @MockBean
    private UserRepository userRepository;

    private final User user = User.builder().build();

    private Optional<User> foundUser;
    private String login = "testLogin";

    @Nested
    class UserByLogin {

        @Test
        void test_findUserByLogin_isPresent() {
            user.setLogin(login);

            when(userRepository.findUserByLogin(login)).thenReturn(Optional.of(user));

            foundUser = userRepository.findUserByLogin(login);

            assertEquals(user, foundUser.get());
            assertEquals(login, foundUser.get().getLogin());
        }

        @Test
        void test_findUserByLogin_isNotPresent() {
            login = "nonExistentUser";

            when(userRepository.findUserByLogin(login)).thenReturn(Optional.empty());

            foundUser = userRepository.findUserByLogin(login);

            assertFalse(foundUser.isPresent());
        }
    }

    @Nested
    class UserById {

        @Test
        void test_findUserById_isPresent() {
            Long id = 1L;
            user.setId(id);

            when(userRepository.findUserById(id)).thenReturn(Optional.of(user));

            foundUser = userRepository.findUserById(id);

            assertEquals(user, foundUser.get());
            assertEquals(id, foundUser.get().getId());
        }

        @Test
        void test_findUserById_isNotPresent() {
            when(userRepository.findUserById(any())).thenReturn(Optional.empty());

            foundUser = userRepository.findUserById(any());

            assertFalse(foundUser.isPresent());
        }
    }

    @Nested
    class UserByLoginOrEmail {

        private String email = "testEmail";

        @Test
        void test_findUserByLoginOrEmail_isPresentByLogin() {
            user.setLogin(login);
            user.setEmail(email);

            when(userRepository.findUserByLoginOrEmail(login, email)).thenReturn(Optional.of(user));

            foundUser = userRepository.findUserByLoginOrEmail(login, email);

            assertEquals(user, foundUser.get());
            assertEquals(login, foundUser.get().getLogin());
        }

        @Test
        void test_findUserByLoginOrEmail_isPresentByEmail() {
            user.setLogin(login);
            user.setEmail(email);

            when(userRepository.findUserByLoginOrEmail(login, email)).thenReturn(Optional.of(user));

            foundUser = userRepository.findUserByLoginOrEmail(login, email);

            assertEquals(user, foundUser.get());
            assertEquals(email, foundUser.get().getEmail());
        }

        @Test
        void test_findUserByLoginOrEmail_isNotPresent() {
            login = "incorrectTestLogin";
            user.setLogin(login);
            email = "incorrectTestEmail";
            user.setEmail(email);

            when(userRepository.findUserByLoginOrEmail(login, email)).thenReturn(Optional.empty());

            foundUser = userRepository.findUserByLoginOrEmail(login, email);

            assertFalse(foundUser.isPresent());
        }
    }
}
