package by.tms.eshop.repository;

import by.tms.eshop.domain.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByRole(String role);
}
