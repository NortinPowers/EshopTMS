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
//@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserDetails userDetails;
        CustomUserDetail userDetails;
        Optional<User> user = userService.getUserByLogin(username);
        if (user.isPresent()) {
            userDetails = new CustomUserDetail(user.get());
//            String userUuid = randomUUID().toString();
//            MDC.put(CONVERSATION, userUuid);
//            log.info("The user with a login " + userDetails.getUser().getLogin() + " is logged in, has been assigned a UUID [" + userUuid + "]");
//            userDetails = new org.springframework.security.core.userdetails.User(user.get().getLogin(), user.get().getPassword(), Collections.emptySet());
        } else {
            throw new UsernameNotFoundException("User wasn't found");
        }
        return userDetails;
    }
}
