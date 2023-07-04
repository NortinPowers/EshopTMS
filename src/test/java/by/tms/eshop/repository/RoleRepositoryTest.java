package by.tms.eshop.repository;

import static by.tms.eshop.test_utils.Constants.TEST_PROPERTY_SOURCE_LOCATIONS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import by.tms.eshop.domain.Role;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest
@TestPropertySource(locations = TEST_PROPERTY_SOURCE_LOCATIONS)
@Sql(value = "classpath:sql/role/role-repository-before.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:sql/role/role-repository-after.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    private String testRole = "ROLE_USER";

    @Test
    void test_findRoleByRole_isPresent() {
        Optional<Role> roleByRole = roleRepository.findRoleByRole(testRole);

        assertTrue(roleByRole.isPresent());
        assertEquals(testRole, roleByRole.get().getRole());
    }

    @Test
    void test_findRoleByRole_isNotPresent() {
        testRole = "NotExistRole";

        Optional<Role> roleByRole = roleRepository.findRoleByRole(testRole);

        assertFalse(roleByRole.isPresent());
    }
}
