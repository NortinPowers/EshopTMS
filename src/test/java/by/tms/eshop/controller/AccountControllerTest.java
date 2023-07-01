package by.tms.eshop.controller;

import static by.tms.eshop.test_utils.Constants.TEST_PROPERTY_SOURCE_LOCATIONS;
import static by.tms.eshop.test_utils.ControllerUtils.getCustomUserDetailRoleAdmin;
import static by.tms.eshop.test_utils.ControllerUtils.getCustomUserDetailRoleUser;
import static by.tms.eshop.utils.Constants.Attributes.USER_ORDER;
import static by.tms.eshop.utils.Constants.MappingPath.ACCOUNT;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import by.tms.eshop.dto.OrderDto;
import by.tms.eshop.security.CustomUserDetail;
import by.tms.eshop.service.OrderService;
import java.util.Collections;
import java.util.List;
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
@TestPropertySource(locations = TEST_PROPERTY_SOURCE_LOCATIONS)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${application.base-url}")
    private String baseUrl;

    @MockBean
    private OrderService orderService;

    @Test
    @WithAnonymousUser
    void test_showAccountPage_anonymous_denied() throws Exception {
        mockMvc.perform(get("/account"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl(baseUrl + "/login"));
    }

    @Test
    void test_showAccountPage_roleUser_allowed() throws Exception {
        CustomUserDetail customUserDetail = getCustomUserDetailRoleUser();
        inspectShowAccountPageByUserRole(customUserDetail);
    }

    @Test
    void test_showAccountPage_roleAdmin_allowed() throws Exception {
        CustomUserDetail customUserDetail = getCustomUserDetailRoleAdmin();
        inspectShowAccountPageByUserRole(customUserDetail);
    }

    private void inspectShowAccountPageByUserRole(CustomUserDetail customUserDetail) throws Exception {
        List<OrderDto> orders = Collections.emptyList();

        when(orderService.getOrdersById(customUserDetail.getUser().getId())).thenReturn(orders);

        mockMvc.perform(get("/account")
                                .with(user(customUserDetail)))
               .andExpect(status().isOk())
               .andExpect(model().attribute(USER_ORDER, orders))
               .andExpect(view().name(ACCOUNT));
    }
}
