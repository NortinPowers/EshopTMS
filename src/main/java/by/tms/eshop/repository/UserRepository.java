package by.tms.eshop.repository;

import by.tms.eshop.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> getUserByLogin(String login);

    void addUser(User user);

    Optional<User> getVerifyUser(String login, String email);
}