package by.tms.eshop.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import by.tms.eshop.domain.Role;
import by.tms.eshop.domain.User;
import by.tms.eshop.security.CustomUserDetail;
import by.tms.eshop.service.OrderService;
import by.tms.eshop.utils.ControllerUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ControllerUtils controllerUtils;

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
//    @WithMockUser(username = "user", roles = "USER")
    void showAccountPageRedirectAllowed() throws Exception {
        User user = new User();
        user.setLogin("test");
        user.setPassword("test");
        user.setRole(Role.builder()
                         .role("ROLE_USER")
                         .build());
        CustomUserDetail customUserDetail  = new CustomUserDetail(user);
//        Mockito.when(orderService.getOrdersById(1L)).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/account").with(user(customUserDetail)))

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        mockMvc.perform(get("/account").with(user(((UserDetails) authentication.getPrincipal()).getUsername())))

               .andExpect(status().isOk())
               .andExpect(view().name("account/account"));

    }

//    @Test
////    @WithMockUser(roles = "USER")
//    @WithMockUser
//    void showAccountPageRedirectAllowedNew() throws Exception {
////        User user = new User();
////        user.setLogin("test");
////        user.setPassword("test");
////        user.setRole(Role.builder().role("ROLE_USER").build());
////        CustomUserDetail customUserDetail  = new CustomUserDetail(user);
////        Mockito.when(orderService.getOrdersById(1L)).thenReturn(Collections.emptyList());
//        Mockito.when(getAuthenticationUser()).thenReturn(User.builder()
//                                                                                                         .login("user")
//                                                                                                         .password("test")
//                                                                                                         .role(Role.builder()
//                                                                                                                   .role("ROLE_USER")
//                                                                                                                   .build())
//                                                                                                         .build());
//        Mockito.when(getAuthenticationUserId()).thenReturn(1L);
//        mockMvc.perform(get("/account"))
//               .andDo(print())
//
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//////        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
////        mockMvc.perform(get("/account").with(user(((UserDetails) authentication.getPrincipal()).getUsername())))
//
//               .andExpect(status().isOk())
//               .andExpect(view().name("account/account"));
//
//    }
}