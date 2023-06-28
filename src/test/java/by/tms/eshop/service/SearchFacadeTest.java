package by.tms.eshop.service;

import static by.tms.eshop.test_utils.Constants.TV;
import static by.tms.eshop.utils.Constants.ALL;
import static by.tms.eshop.utils.Constants.AND_SIZE;
import static by.tms.eshop.utils.Constants.Attributes.FILTER_FOUND_PRODUCTS;
import static by.tms.eshop.utils.Constants.Attributes.FOUND_PRODUCTS;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_FILTER_TRUE_RESULT_SAVE;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_RESULT_SAVE;
import static by.tms.eshop.utils.Constants.RequestParameters.MAX_PRICE;
import static by.tms.eshop.utils.Constants.RequestParameters.MIN_PRICE;
import static by.tms.eshop.utils.Constants.SEARCH_PAGE_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.tms.eshop.dto.ProductDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;

@SpringBootTest
class SearchFacadeTest {

    @Autowired
    private SearchFacade searchFacade;

    @MockBean
    private ProductService productService;

    private ModelAndView modelAndView = new ModelAndView();
    private final HttpSession session = new MockHttpSession();
    private final HttpServletRequest request = new MockHttpServletRequest();
    private final String path = REDIRECT_TO_SEARCH_RESULT_SAVE + AND_SIZE + SEARCH_PAGE_SIZE;

    @Nested
    class ProductsPageBySearchCondition {

        private String searchCondition;

        @Test
        void test_getProductsPageBySearchCondition_notEmptyCondition() {
            searchCondition = "some";
            ProductDto productDto = ProductDto.builder().build();
            Set<ProductDto> products = Set.of(productDto);

            when(productService.getFoundedProducts(searchCondition)).thenReturn(products);
            modelAndView = searchFacade.getProductsPageBySearchCondition(session, searchCondition);

            assertEquals(products, session.getAttribute(FOUND_PRODUCTS));
            assertEquals(path, modelAndView.getViewName());
        }

        @Test
        void test_getProductsPageBySearchCondition_emptyCondition() {
            searchCondition = "";

            modelAndView = searchFacade.getProductsPageBySearchCondition(session, searchCondition);

            assertEquals(path, modelAndView.getViewName());
            verify(productService, never()).getFoundedProducts(searchCondition);
        }
    }

    @Nested
    class SearchFilterResultPagePath {

        private final BigDecimal minPrice = new BigDecimal("500");
        private final BigDecimal maxPrice = new BigDecimal("1000");
        private final String category = TV;
        private final ProductDto productDtoOne = getProductDto(TV, 600L);
        private final ProductDto productDtoTwo = getProductDto("phone", 600L);
        private final ProductDto productDtoThree = getProductDto(TV, 300L);
        private final Set<ProductDto> products = Set.of(productDtoOne, productDtoTwo, productDtoThree);

        @Test
        void test_getSearchFilterResultPagePath_foundProductsNotEmpty() {
            request.setAttribute(MIN_PRICE, minPrice);
            request.setAttribute(MAX_PRICE, maxPrice);
            session.setAttribute(FOUND_PRODUCTS, products);
            ((MockHttpServletRequest) request).setSession(session);

            modelAndView = searchFacade.getSearchFilterResultPagePath(request, category);

            assertEquals(Set.of(productDtoOne), session.getAttribute(FILTER_FOUND_PRODUCTS));
            assertEquals(REDIRECT_TO_SEARCH_FILTER_TRUE_RESULT_SAVE + AND_SIZE + SEARCH_PAGE_SIZE, modelAndView.getViewName());
        }

        @Test
        void test_getSearchFilterResultPagePath_foundProductsEmpty_allCategories() {
            request.setAttribute(MIN_PRICE, minPrice);
            request.setAttribute(MAX_PRICE, maxPrice);
            ((MockHttpServletRequest) request).setSession(session);

            when(productService.selectProductsFromCategoryByFilter(category, minPrice, maxPrice)).thenReturn(products);
            modelAndView = searchFacade.getSearchFilterResultPagePath(request, category);

            assertEquals(products, session.getAttribute(FOUND_PRODUCTS));
            assertEquals(path, modelAndView.getViewName());
        }

        @Test
        void test_getSearchFilterResultPagePath_foundProductsEmpty_currentCategory() {
            String category = ALL;
            request.setAttribute(MIN_PRICE, minPrice);
            request.setAttribute(MAX_PRICE, maxPrice);
            ((MockHttpServletRequest) request).setSession(session);

            when(productService.selectAllProductsByFilter(minPrice, maxPrice)).thenReturn(products);
            modelAndView = searchFacade.getSearchFilterResultPagePath(request, category);

            assertEquals(products, session.getAttribute(FOUND_PRODUCTS));
            assertEquals(path, modelAndView.getViewName());
        }

        private ProductDto getProductDto(String category, Long price) {
            return ProductDto.builder()
                             .category(category)
                             .price(BigDecimal.valueOf(price))
                             .build();
        }
    }

    @Nested
    class Pagination{

        @Test
        void test_setPagination_NullSessionAttribute() {
        }
        @Test
        void test_setPagination_NotNullFoundProducts() {
        }
        @Test
        void test_setPagination_NotNullFoundProductsButEmpty() {
        }
        @Test
        void test_setPagination_NotNullFilterFoundProducts() {
        }
        @Test
        void test_setPagination_NotNullFilterFoundProductsButEmpty() {
        }
        @Test
        void test_setPagination_NotNullFoundProductsAndFilterFoundProducts() {
        }
        @Test
        void test_setPagination_NotNullFoundProductsAndFilterFoundProductsButEmpty() {
        }

    }

    @Test
    void processFilter() {
    }
}