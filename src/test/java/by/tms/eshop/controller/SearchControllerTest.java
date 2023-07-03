package by.tms.eshop.controller;

import static by.tms.eshop.test_utils.Constants.RESULT;
import static by.tms.eshop.test_utils.Constants.SIZE;
import static by.tms.eshop.test_utils.Constants.TV;
import static by.tms.eshop.utils.Constants.AND_SIZE;
import static by.tms.eshop.utils.Constants.Attributes.PRODUCT_CATEGORIES;
import static by.tms.eshop.utils.Constants.ControllerMappingPath.ERROR_403;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_FILTER_TRUE_RESULT_SAVE;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_RESULT_SAVE;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SOME_ERROR;
import static by.tms.eshop.utils.Constants.MappingPath.SEARCH_PATH;
import static by.tms.eshop.utils.Constants.PAGE;
import static by.tms.eshop.utils.Constants.RequestParameters.FILTER;
import static by.tms.eshop.utils.Constants.RequestParameters.SEARCH_CONDITION;
import static by.tms.eshop.utils.Constants.RequestParameters.SELECT;
import static by.tms.eshop.utils.Constants.SEARCH_PAGE_SIZE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import by.tms.eshop.service.ProductCategoryService;
import by.tms.eshop.service.SearchFacade;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;

@SpringBootTest
@AutoConfigureMockMvc
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchFacade searchFacade;

    @MockBean
    private ProductCategoryService productCategoryService;

    private final HttpSession session = new MockHttpSession();

    @Nested
    class TestHasFilterPage {

        private final List<String> productCategories = List.of(TV);
        private String result;
        private String filter;

        @Test
        @WithAnonymousUser
        void test_hasFilterPage_anonymousUser_allRequestParams() throws Exception {
            inspectHasFilterPageWithAllRequestParams();
        }

        @Test
        @WithMockUser(roles = {"USER", "ADMIN"})
        void test_hasFilterPage_userWithRole_allRequestParams() throws Exception {
            inspectHasFilterPageWithAllRequestParams();
        }

        private void inspectHasFilterPageWithAllRequestParams() throws Exception {
            result = "any";
            filter = "false";

            doNothing().when(searchFacade).processFilter(session, result, filter);
            when(productCategoryService.getProductCategories()).thenReturn(productCategories);
            doNothing().when(searchFacade).setPagination(eq(session), any(Pageable.class), any(ModelAndView.class));

            mockMvc.perform(get("/search")
                                    .param(RESULT, result)
                                    .param(FILTER, filter)
                                    .param(PAGE, "0")
                                    .param(SIZE, "5"))
                   .andExpect(status().isOk())
                   .andExpect(model().attribute(PRODUCT_CATEGORIES, productCategories))
                   .andExpect(view().name(SEARCH_PATH));
        }

        @Test
        @WithAnonymousUser
        void test_hasFilterPage_anonymousUser_onlyPageableParam() throws Exception {
            inspectHasFilterPageOnlyPageableParam();
        }

        @Test
        @WithMockUser(roles = {"USER", "ADMIN"})
        void test_hasFilterPage_userWithRole_onlyPageableParam() throws Exception {
            inspectHasFilterPageOnlyPageableParam();
        }

        private void inspectHasFilterPageOnlyPageableParam() throws Exception {
            doNothing().when(searchFacade).processFilter(session, result, filter);
            when(productCategoryService.getProductCategories()).thenReturn(productCategories);
            doNothing().when(searchFacade).setPagination(eq(session), any(Pageable.class), any(ModelAndView.class));

            mockMvc.perform(get("/search")
                                    .param(PAGE, "0")
                                    .param(SIZE, "5"))
                   .andExpect(status().isOk())
                   .andExpect(model().attribute(PRODUCT_CATEGORIES, productCategories))
                   .andExpect(view().name(SEARCH_PATH));
        }

        @Test
        @WithAnonymousUser
        void test_hasFilterPage_anonymousUser_noParams() throws Exception {
            inspectHasFilterPageNoParams();
        }

        @Test
        @WithMockUser(roles = {"USER", "ADMIN"})
        void test_hasFilterPage_userWithRole_noParams() throws Exception {
            inspectHasFilterPageNoParams();
        }

        private void inspectHasFilterPageNoParams() throws Exception {
            doNothing().when(searchFacade).processFilter(session, result, filter);
            when(productCategoryService.getProductCategories()).thenReturn(productCategories);
            doNothing().when(searchFacade).setPagination(eq(session), eq(null), any(ModelAndView.class));

            mockMvc.perform(get("/search"))
                   .andExpect(status().isOk())
                   .andExpect(model().attribute(PRODUCT_CATEGORIES, productCategories))
                   .andExpect(view().name(SEARCH_PATH));
        }
    }

    @Nested
    class TestShowSearchPageByParam {

        private final String url = "/search-param";
        private final String path = REDIRECT_TO_SEARCH_RESULT_SAVE + AND_SIZE + SEARCH_PAGE_SIZE;

        @Test
        @WithAnonymousUser
        void test_showSearchPageByParam_csrfNotContained() throws Exception {
            mockMvc.perform(post(url))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(redirectedUrl(ERROR_403));
        }

        @Test
        @WithAnonymousUser
        void test_showSearchPageByParam_anonymousUser_allRequestParams() throws Exception {
            inspectShowSearchPageByParamWithAllRequestParams();

        }

        @Test
        @WithMockUser(roles = {"USER", "ADMIN"})
        void test_showSearchPageByParam_userWithRole_allRequestParams() throws Exception {
            inspectShowSearchPageByParamWithAllRequestParams();

        }

        private void inspectShowSearchPageByParamWithAllRequestParams() throws Exception {
            ModelAndView modelAndView = new ModelAndView(path);
            String searchCondition = "some";

            when(searchFacade.getProductsPageBySearchCondition(any(), eq(searchCondition))).thenReturn(modelAndView);

            mockMvc.perform(post(url)
                                    .with(csrf())
                                    .param(SEARCH_CONDITION, searchCondition)
                                    .sessionAttr(FILTER, new Object()))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(view().name(path));
        }

        @Test
        @WithAnonymousUser
        void test_showSearchPageByParam_anonymousUser_noRequestParams() throws Exception {
            inspectShowSearchPageByParamWithoutRequestParams();

        }

        @Test
        @WithMockUser(roles = {"USER", "ADMIN"})
        void test_showSearchPageByParam_userWithRole_noRequestParams() throws Exception {
            inspectShowSearchPageByParamWithoutRequestParams();

        }

        private void inspectShowSearchPageByParamWithoutRequestParams() throws Exception {
            mockMvc.perform(post(url)
                                    .with(csrf()))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(view().name(REDIRECT_TO_SOME_ERROR));
        }
    }

    @Nested
    class TestShowSearchPageByFilter {

        private final String url = "/search-filter";
        private final String path = REDIRECT_TO_SEARCH_FILTER_TRUE_RESULT_SAVE + AND_SIZE + SEARCH_PAGE_SIZE;
        private final ModelAndView modelAndView = new ModelAndView(path);
        private String type = TV;

        @Test
        @WithAnonymousUser
        void test_showSearchPageByFilter_csrfNotContained() throws Exception {
            mockMvc.perform(post(url))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(redirectedUrl(ERROR_403));
        }

        @Test
        @WithAnonymousUser
        void test_showSearchPageByFilter_anonymousUser_allRequestParams() throws Exception {
            inspectShowSearchPageByFilterWithAllRequestParams();

        }

        @Test
        @WithMockUser(roles = {"USER", "ADMIN"})
        void test_showSearchPageByFilter_userWithRole_allRequestParams() throws Exception {
            inspectShowSearchPageByFilterWithAllRequestParams();

        }

        private void inspectShowSearchPageByFilterWithAllRequestParams() throws Exception {
            when(searchFacade.getSearchFilterResultPagePath(any(), eq(type))).thenReturn(modelAndView);

            mockMvc.perform(post(url)
                                    .with(csrf())
                                    .param(SELECT, type))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(view().name(path));
        }

        @Test
        @WithAnonymousUser
        void test_showSearchPageByFilter_anonymousUser_noRequestParams() throws Exception {
            inspectShowSearchPageByFilterWithoutRequestParams();

        }

        @Test
        @WithMockUser(roles = {"USER", "ADMIN"})
        void test_showSearchPageByFilter_userWithRole_noRequestParams() throws Exception {
            inspectShowSearchPageByFilterWithoutRequestParams();

        }

        private void inspectShowSearchPageByFilterWithoutRequestParams() throws Exception {
            type = null;

            when(searchFacade.getSearchFilterResultPagePath(any(), eq(type))).thenReturn(modelAndView);

            mockMvc.perform(post(url)
                                    .with(csrf()))
                   .andExpect(status().is3xxRedirection())
                   .andExpect(view().name(path));
        }
    }
}
