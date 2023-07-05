package by.tms.eshop.controller;

import static by.tms.eshop.test_utils.Constants.PHONE;
import static by.tms.eshop.test_utils.Constants.TV;
import static by.tms.eshop.utils.Constants.Attributes.PRODUCT_CATEGORIES;
import static by.tms.eshop.utils.Constants.ControllerMappingPath.ERROR_403;
import static by.tms.eshop.utils.Constants.MappingPath.ESHOP;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import by.tms.eshop.service.ProductCategoryService;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductCategoryService productCategoryService;

    private final List<String> productCategories = List.of(TV, PHONE);
    private final String url = "/eshop";

    @Nested
    class TestRedirectToEshopPage {

        @Test
        @WithAnonymousUser
        void test_redirectToEshopPage_anonymous_allowed() throws Exception {
            inspectRedirectToEshopPage();
        }

        @Test
        @WithMockUser(roles = {"USER", "ADMIN"})
        void test_redirectToEshopPage_userWithRole_allowed() throws Exception {
            inspectRedirectToEshopPage();
        }

        private void inspectRedirectToEshopPage() throws Exception {
            when(productCategoryService.getProductCategories()).thenReturn(productCategories);

            mockMvc.perform(get(url))
                   .andExpect(status().isOk())
                   .andExpect(model().attribute(PRODUCT_CATEGORIES, productCategories))
                   .andExpect(view().name(ESHOP));
        }
    }

    @Nested
    class TestEntersToEshop {

        @Test
        @WithAnonymousUser
        void test_entersToEshop_anonymous_allowed() throws Exception {
            inspectEntersToEshop();
        }

        @Test
        @WithAnonymousUser
        void test_entersToEshop_anonymous_csrfNotContained() throws Exception {
            mockMvc.perform(post(url))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(redirectedUrl(ERROR_403));
        }

        @Test
        @WithMockUser(roles = {"USER", "ADMIN"})
        void test_entersToEshop_userWithRole_allowed() throws Exception {
            inspectEntersToEshop();
        }

        private void inspectEntersToEshop() throws Exception {
            when(productCategoryService.getProductCategories()).thenReturn(productCategories);

            mockMvc.perform(post(url)
                                    .with(csrf()))
                   .andExpect(status().isOk())
                   .andExpect(model().attribute(PRODUCT_CATEGORIES, productCategories))
                   .andExpect(view().name(ESHOP));
        }
    }
}
