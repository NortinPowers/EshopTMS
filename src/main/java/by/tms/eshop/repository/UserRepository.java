package by.tms.eshop.repository;

import by.tms.eshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByLogin(String login);

    User saveAndFlush(User user);

    Optional<User> findUserByLoginOrEmail(String login, String email);
}