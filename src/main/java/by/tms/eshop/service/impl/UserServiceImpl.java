package by.tms.eshop.service.impl;

import by.tms.eshop.domain.User;
import by.tms.eshop.repository.UserRepository;
import by.tms.eshop.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    @Transactional
    @Override
    public void addUser(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public Optional<User> getVerifyUser(String login, String email) {
        return userRepository.findUserByLoginOrEmail(login, email);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findUserById(id);
    }
}
