package by.tms.eshop.service.impl;

import by.tms.eshop.domain.User;
import by.tms.eshop.repository.UserRepository;
import by.tms.eshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getUserByLogin(String login) {
        return userRepository.getUserByLogin(login);
    }

    @Override
    public void addUser(User user) {
        userRepository.addUser(user);
    }

    @Override
    public Optional<User> getVerifyUser(String login, String email) {
        return userRepository.getVerifyUser(login, email);
    }
}