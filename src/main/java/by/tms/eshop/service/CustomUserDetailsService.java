package by.tms.eshop.service;

import by.tms.eshop.domain.User;
import by.tms.eshop.security.CustomUserDetail;
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
        User user = userService.getUserByLogin(username).orElseThrow(() -> new UsernameNotFoundException("User wasn't found"));
        return new CustomUserDetail(user);
    }
}
