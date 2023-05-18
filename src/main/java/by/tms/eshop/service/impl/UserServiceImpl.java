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
//        return userRepository.getUserByLogin(login);
        return userRepository.findUserByLogin(login);
    }

    @Override
    public void addUser(User user) {
//        userRepository.addUser(user);
        userRepository.saveAndFlush(user);
//        userRepository.save(user);
    }

    @Override
    public Optional<User> getVerifyUser(String login, String email) {
//        return userRepository.getVerifyUser(login, email);
//        return userRepository.findUserByLoginAndEmail(login, email);
        return userRepository.findUserByLoginOrEmail(login, email);
    }
}