package by.tms.eshop.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import by.tms.eshop.domain.Role;
import by.tms.eshop.domain.User;
import by.tms.eshop.dto.RoleDto;
import by.tms.eshop.dto.UserFormDto;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    private final Long userId = 1L;
    private final Long roleId = 1L;
    private final String login = "login";
    private final String password = "password";
    private final String name = "name";
    private final String surname = "surname";
    private final String email = "test@email.com";
    private final LocalDate birthday = LocalDate.of(2000, 1, 1);
    private final String userRole = "role";
    private final RoleDto roleDto = RoleDto.builder()
                                           .id(roleId)
                                           .role(userRole)
                                           .build();
    private final Role role = Role.builder()
                                  .id(roleId)
                                  .role(userRole)
                                  .build();
    private final UserFormDto userFormDto = UserFormDto.builder()
                                                       .id(userId)
                                                       .login(login)
                                                       .password(password)
                                                       .name(name)
                                                       .surname(surname)
                                                       .email(email)
                                                       .birthday(birthday)
                                                       .roleDto(roleDto)
                                                       .build();
    private final User user = User.builder()
                                  .id(userId)
                                  .login(login)
                                  .password(password)
                                  .name(name)
                                  .surname(surname)
                                  .email(email)
                                  .birthday(birthday)
                                  .role(role)
                                  .build();

    @Test
    void test_convertToUser() {
        String verifyPassword = "password";
        userFormDto.setVerifyPassword(verifyPassword);

        User convertUser = userMapper.convertToUser(userFormDto);

        assertEquals(user, convertUser);
    }

    @Test
    void test_convertToUserFormDto() {
        UserFormDto convertUser = userMapper.convertToUserFormDto(user);

        assertEquals(userFormDto, convertUser);
    }
}
