package by.tms.eshop.service;

import by.tms.eshop.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserService {

    Optional<User> getUserByLogin(String login);

    @Transactional
    void addUser(User user);

    Optional<User> getVerifyUser(String login, String email);
}