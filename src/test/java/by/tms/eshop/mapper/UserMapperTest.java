package by.tms.eshop.mapper;

import static by.tms.eshop.test_utils.Constants.MapperConstants.ROLE_DTO;
import static by.tms.eshop.test_utils.Constants.MapperConstants.USER;
import static by.tms.eshop.test_utils.Constants.MapperConstants.USER_BIRTHDAY;
import static by.tms.eshop.test_utils.Constants.MapperConstants.USER_EMAIL;
import static by.tms.eshop.test_utils.Constants.MapperConstants.USER_ID;
import static by.tms.eshop.test_utils.Constants.MapperConstants.USER_LOGIN;
import static by.tms.eshop.test_utils.Constants.MapperConstants.USER_NAME;
import static by.tms.eshop.test_utils.Constants.MapperConstants.USER_PASSWORD;
import static by.tms.eshop.test_utils.Constants.MapperConstants.USER_SURNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

import by.tms.eshop.domain.User;
import by.tms.eshop.dto.UserFormDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    private final UserFormDto userFormDto = UserFormDto.builder()
                                                       .id(USER_ID)
                                                       .login(USER_LOGIN)
                                                       .password(USER_PASSWORD)
                                                       .name(USER_NAME)
                                                       .surname(USER_SURNAME)
                                                       .email(USER_EMAIL)
                                                       .birthday(USER_BIRTHDAY)
                                                       .roleDto(ROLE_DTO)
                                                       .build();

    @Test
    void test_convertToUser() {
        String verifyPassword = USER_PASSWORD;
        userFormDto.setVerifyPassword(verifyPassword);

        User convertUser = userMapper.convertToUser(userFormDto);

        assertEquals(USER, convertUser);
    }

    @Test
    void test_convertToUserFormDto() {
        UserFormDto convertUser = userMapper.convertToUserFormDto(USER);

        assertEquals(userFormDto, convertUser);
    }
}
