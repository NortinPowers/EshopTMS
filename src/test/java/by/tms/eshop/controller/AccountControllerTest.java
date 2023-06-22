package by.tms.eshop.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import by.tms.eshop.domain.Role;
import by.tms.eshop.domain.User;
import by.tms.eshop.security.CustomUserDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${application.base-url}")
    private String baseUrl;

    @Test
    @WithAnonymousUser
    void showAccountPageRedirectDenied() throws Exception {
        mockMvc.perform(get("/account"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl( baseUrl+"/login"));
    }

    @Test
    void showAccountPageRedirectAllowed() throws Exception {
        User user = new User();
        user.setLogin("test");
        user.setPassword("test");
        user.setRole(Role.builder()
                         .role("ROLE_USER")
                         .build());
        CustomUserDetail customUserDetail  = new CustomUserDetail(user);
        mockMvc.perform(get("/account").with(user(customUserDetail)))
               .andExpect(status().isOk())
               .andExpect(view().name("account/account"));
    }
}
