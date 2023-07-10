package by.tms.eshop.controller;

import static by.tms.eshop.test_utils.Constants.MapperConstants.USER_BIRTHDAY;
import static by.tms.eshop.test_utils.Constants.MapperConstants.USER_EMAIL;
import static by.tms.eshop.test_utils.Constants.MapperConstants.USER_LOGIN;
import static by.tms.eshop.test_utils.Constants.MapperConstants.USER_NAME;
import static by.tms.eshop.test_utils.Constants.MapperConstants.USER_PASSWORD;
import static by.tms.eshop.test_utils.Constants.MapperConstants.USER_SURNAME;
import static by.tms.eshop.test_utils.Constants.TEST_PROPERTY_SOURCE_LOCATIONS;
import static by.tms.eshop.test_utils.ControllerUtils.getCustomUserDetailRoleUser;
import static by.tms.eshop.utils.Constants.Attributes.USER;
import static by.tms.eshop.utils.Constants.ControllerMappingPath.ERROR_403;
import static by.tms.eshop.utils.Constants.MappingPath.CREATE_USER;
import static by.tms.eshop.utils.Constants.MappingPath.EDIT;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_ACCOUNT;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SOME_ERROR;
import static by.tms.eshop.utils.Constants.MappingPath.SUCCESS_REGISTER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import by.tms.eshop.dto.UserFormDto;
import by.tms.eshop.security.CustomUserDetail;
import by.tms.eshop.service.ShopFacade;
import by.tms.eshop.utils.Constants.ControllerMappingPath;
import by.tms.eshop.utils.Constants.MappingPath;
import by.tms.eshop.validator.UserValidator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = TEST_PROPERTY_SOURCE_LOCATIONS)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopFacade shopFacade;

    @MockBean
    private UserValidator userValidator;

    @Value("${application.base-url}")
    private String baseUrl;

    private final UserFormDto userFormDto = UserFormDto.builder()
                                                       .login(USER_LOGIN)
                                                       .password(USER_PASSWORD)
                                                       .verifyPassword(USER_PASSWORD)
                                                       .name(USER_NAME)
                                                       .surname(USER_SURNAME)
                                                       .email(USER_EMAIL)
                                                       .birthday(USER_BIRTHDAY)
                                                       .build();

    @Test
    void test_showLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
               .andExpect(status().isOk())
               .andExpect(view().name(MappingPath.LOGIN));
    }

    @Test
    void test_create() throws Exception {
        mockMvc.perform(get("/create-user")
                                .flashAttr(USER, userFormDto))
               .andExpect(status().isOk())
               .andExpect(view().name(CREATE_USER));
    }

    @Nested
    class TestCreateUser {

        private final String url = "/create-user";

        @Test
        void test_createUser_csrfNotContained() throws Exception {
            mockMvc.perform(post(url)
                                    .flashAttr(USER, userFormDto))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(redirectedUrl(ERROR_403));
        }

        @Test
        void test_createUser_success() throws Exception {
            doNothing().when(userValidator).validate(eq(userFormDto), any(Errors.class));
            doNothing().when(shopFacade).createUser(userFormDto);

            mockMvc.perform(post(url)
                                    .with(csrf())
                                    .flashAttr(USER, userFormDto))
                   .andExpect(status().isOk())
                   .andExpect(view().name(SUCCESS_REGISTER));
        }

        @Test
        void test_createUser_validateError() throws Exception {
            userFormDto.setName("No");

            doNothing().when(userValidator).validate(eq(userFormDto), any(Errors.class));
            doNothing().when(shopFacade).createUser(userFormDto);

            mockMvc.perform(post(url)
                                    .with(csrf())
                                    .flashAttr(USER, userFormDto))
                   .andExpect(status().isOk())
                   .andExpect(view().name(CREATE_USER));
        }

        @Test
        void test_createUser_noAttr() throws Exception {
            mockMvc.perform(post(url)
                                    .with(csrf()))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(view().name(REDIRECT_TO_SOME_ERROR));
        }
    }

    @Nested
    class TestEdit {

        private final String url = "/edit-user";

        @Test
        void test_edit_withPathVariable() throws Exception {
            ModelAndView modelAndView = new ModelAndView(EDIT);
            modelAndView.addObject(USER, userFormDto);
            CustomUserDetail customUserDetail = getCustomUserDetailRoleUser();


            when(shopFacade.getUserEditForm(customUserDetail.getUser().getId())).thenReturn(modelAndView);

            mockMvc.perform(get(url)
                                    .with(user(customUserDetail)))
                   .andExpect(status().isOk())
                   .andExpect(view().name(EDIT));
        }

        @Test
        @WithAnonymousUser
        void test_edit_anonymous_denied() throws Exception {
            mockMvc.perform(get(url))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(redirectedUrl(baseUrl + ControllerMappingPath.LOGIN));
        }
    }

    @Nested
    class TestEditUser {

        private final String url = "/edit-user";
        private final UserFormDto user = UserFormDto.builder()
                                                    .name(USER_NAME)
                                                    .surname(USER_SURNAME)
                                                    .build();
        private final CustomUserDetail customUserDetail = getCustomUserDetailRoleUser();

        @Test
        @WithAnonymousUser
        void test_editUser_anonymous_denied() throws Exception {
            mockMvc.perform(post(url)
                                    .with(csrf())
                                    .flashAttr(USER, user))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(redirectedUrl(baseUrl + ControllerMappingPath.LOGIN));
        }

        @Test
        void test_editUser_csrfNotContained() throws Exception {
            mockMvc.perform(post(url)
                                    .with(user(customUserDetail))
                                    .flashAttr(USER, user))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(redirectedUrl(ERROR_403));
        }

        @Test
        void test_editUser_success() throws Exception {
            doNothing().when(shopFacade).editUser(userFormDto);

            mockMvc.perform(post(url)
                                    .with(csrf())
                                    .with(user(customUserDetail))
                                    .flashAttr(USER, userFormDto))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(view().name(REDIRECT_TO_ACCOUNT));
        }

        @Test
        void test_editUser_validateError() throws Exception {
            userFormDto.setName("No");

            doNothing().when(shopFacade).editUser(userFormDto);

            mockMvc.perform(post(url)
                                    .with(csrf())
                                    .with(user(customUserDetail))
                                    .flashAttr(USER, userFormDto))
                   .andExpect(status().isOk())
                   .andExpect(view().name(EDIT));
        }

        @Test
        void test_editUser_noAttr() throws Exception {
            mockMvc.perform(post(url)
                                    .with(csrf())
                                    .with(user(customUserDetail)))
                   .andExpect(status().isOk())
                   .andExpect(view().name(EDIT));
        }
    }
}
