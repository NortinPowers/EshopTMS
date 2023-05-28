package by.tms.eshop.repository;

import by.tms.eshop.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByLogin(String login);

    User saveAndFlush(User user);

    Optional<User> findUserByLoginOrEmail(String login, String email);
}