package by.tms.eshop.controller;

import static by.tms.eshop.test_utils.Constants.TEST_PROPERTY_SOURCE_LOCATIONS;
import static by.tms.eshop.utils.Constants.ControllerMappingPath.ADMIN_INFO;
import static by.tms.eshop.utils.Constants.ControllerMappingPath.ERROR_403;
import static by.tms.eshop.utils.Constants.ControllerMappingPath.LOGIN;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import by.tms.eshop.service.CartService;
import java.util.Collections;
import org.junit.jupiter.api.Test;
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
@TestPropertySource(locations = TEST_PROPERTY_SOURCE_LOCATIONS)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${application.base-url}")
    private String baseUrl;

    @MockBean
    private CartService cartService;

    @Test
    @WithAnonymousUser
    void test_showAdminPage_anonymous_denied() throws Exception {
        mockMvc.perform(get("/admin"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl(baseUrl + LOGIN));

    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void test_showAdminPage_roleUser_denied() throws Exception {
        mockMvc.perform(get("/admin"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl(ERROR_403));

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void test_showAdminPage_roleAdmin_allowed() throws Exception {
        when(cartService.getMostFavorite()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin"))
               .andExpect(status().isOk())
               .andExpect(view().name(ADMIN_INFO));
    }
}
