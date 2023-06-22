package by.tms.eshop.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import by.tms.eshop.service.CartService;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${application.base-url}")
    private String baseUrl;

    @MockBean CartService cartService;

    @Test
    @WithAnonymousUser
    void showAdminPageDeniedAnonymous() throws Exception {
        mockMvc.perform(get("/admin"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl( baseUrl+"/login"));

    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void showAdminPageDeniedNotAdmin() throws Exception {
        mockMvc.perform(get("/admin"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl( "/error-403"));

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void showAdminPageAllowed() throws Exception {
        Mockito.when(cartService.getMostFavorite()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/admin"))
               .andExpect(status().isOk())
               .andExpect(view().name("/admin/info"));
    }
}
