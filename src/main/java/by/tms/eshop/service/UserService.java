package by.tms.eshop.service;

import by.tms.eshop.domain.User;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    Optional<User> getUserByLogin(String login);

    @Transactional
    void addUser(User user);

    Optional<User> getVerifyUser(String login, String email);
}