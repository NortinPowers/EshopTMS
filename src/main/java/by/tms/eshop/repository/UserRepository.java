package by.tms.eshop.repository;

import by.tms.eshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    Optional<User> getUserByLogin(String login);
    Optional<User> findUserByLogin(String login);

//    void addUser(User user);
    User saveAndFlush(User user);

//    Optional<User> getVerifyUser(String login, String email);
//    Optional<User> findUserByLoginAndEmail(String login, String email);
    Optional<User> findUserByLoginOrEmail(String login, String email);
}