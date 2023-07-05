package by.tms.eshop.mapper;

import static by.tms.eshop.test_utils.Constants.MapperConstants.ROLE;
import static by.tms.eshop.test_utils.Constants.MapperConstants.ROLE_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

import by.tms.eshop.dto.RoleDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RoleMapperTest {

    @Autowired
    private RoleMapper roleMapper;

    @Test
    void test_convertToRoleDto() {
        RoleDto conrertedRoleDto = roleMapper.convertToRoleDto(ROLE);

        assertEquals(ROLE_DTO, conrertedRoleDto);
    }
}
