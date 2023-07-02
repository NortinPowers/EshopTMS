package by.tms.eshop.controller;

import static by.tms.eshop.test_utils.Constants.TEST_PROPERTY_SOURCE_LOCATIONS;
import static by.tms.eshop.test_utils.ControllerUtils.getCustomUserDetailRoleAdmin;
import static by.tms.eshop.test_utils.ControllerUtils.getCustomUserDetailRoleUser;
import static by.tms.eshop.utils.Constants.ControllerMappingPath.LOGIN;
import static by.tms.eshop.utils.Constants.MappingPath.ERROR_403;
import static by.tms.eshop.utils.Constants.MappingPath.ERROR_500;
import static by.tms.eshop.utils.Constants.MappingPath.SOME_ERROR;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import by.tms.eshop.security.CustomUserDetail;
import org.junit.jupiter.api.Nested;
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
@TestPropertySource(locations = TEST_PROPERTY_SOURCE_LOCATIONS)
class ExceptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${application.base-url}")
    private String baseUrl;

    private final CustomUserDetail customUserDetailRoleAdmin = getCustomUserDetailRoleAdmin();
    private final CustomUserDetail customUserDetailRoleUser = getCustomUserDetailRoleUser();

    @Nested
    class TestShowError500Page {

        @Test
        @WithAnonymousUser
        void test_showError500Page_anonymous_denied() throws Exception {
            mockMvc.perform(get("/error-500"))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(redirectedUrl(baseUrl + LOGIN));
        }

        @Test
        void test_showError500Page_roleUser_allowed() throws Exception {
            mockMvc.perform(get("/error-500")
                                    .with(user(customUserDetailRoleUser)))
                   .andExpect(status().isOk())
                   .andExpect(view().name(ERROR_500));
        }

        @Test
        void test_showError500Page_roleAdmin_allowed() throws Exception {
            mockMvc.perform(get("/error-500")
                                    .with(user(customUserDetailRoleAdmin)))
                   .andExpect(status().isOk())
                   .andExpect(view().name(ERROR_500));
        }
    }

    @Nested
    class TestShowSomeErrorPage {

        @Test
        @WithAnonymousUser
        void test_showSomeErrorPage_anonymous_denied() throws Exception {
            mockMvc.perform(get("/some-error"))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(redirectedUrl(baseUrl + LOGIN));
        }

        @Test
        void test_showSomeErrorPage_roleUser_allowed() throws Exception {
            mockMvc.perform(get("/some-error")
                                    .with(user(customUserDetailRoleUser)))
                   .andExpect(status().isOk())
                   .andExpect(view().name(SOME_ERROR));
        }

        @Test
        void test_showSomeErrorPage_roleAdmin_allowed() throws Exception {
            mockMvc.perform(get("/some-error")
                                    .with(user(customUserDetailRoleAdmin)))
                   .andExpect(status().isOk())
                   .andExpect(view().name(SOME_ERROR));
        }
    }

    @Nested
    class TestShowError403Page {

        @Test
        void test_showError403Page_anonymous_denied() throws Exception {
            mockMvc.perform(get("/error-403"))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(redirectedUrl(baseUrl + LOGIN));
        }

        @Test
        void test_showError403Page_roleUser_allowed() throws Exception {
            mockMvc.perform(get("/error-403")
                                    .with(user(customUserDetailRoleUser)))
                   .andExpect(status().isOk())
                   .andExpect(view().name(ERROR_403));
        }

        @Test
        void test_showError403Page_roleAdmin_allowed() throws Exception {
            mockMvc.perform(get("/error-403")
                                    .with(user(customUserDetailRoleAdmin)))
                   .andExpect(status().isOk())
                   .andExpect(view().name(ERROR_403));
        }
    }
}
