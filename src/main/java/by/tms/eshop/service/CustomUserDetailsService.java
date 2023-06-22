package by.tms.eshop.service;

import by.tms.eshop.domain.User;
import by.tms.eshop.security.CustomUserDetail;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUserDetail userDetails;
        Optional<User> user = userService.getUserByLogin(username);
        if (user.isPresent()) {
            userDetails = new CustomUserDetail(user.get());
        } else {
            throw new UsernameNotFoundException("User wasn't found");
        }
        return userDetails;
    }
}
