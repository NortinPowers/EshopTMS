package by.tms.eshop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import by.tms.eshop.domain.Role;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class RoleRepositoryTest {

    @MockBean
    private RoleRepository roleRepository;

    private final String testRole = "testRole";

    @Test
    void test_findRoleByRole_isPresent() {
        Role role = Role.builder()
                        .role(testRole)
                        .build();

        when(roleRepository.findRoleByRole(testRole)).thenReturn(Optional.of(role));

        Optional<Role> roleByRole = roleRepository.findRoleByRole(testRole);

        assertEquals(testRole, roleByRole.get().getRole());
    }

    @Test
    void test_findRoleByRole_isNotPresent() {
        when(roleRepository.findRoleByRole(testRole)).thenReturn(Optional.empty());

        Optional<Role> roleByRole = roleRepository.findRoleByRole(testRole);

        assertFalse(roleByRole.isPresent());
    }
}
