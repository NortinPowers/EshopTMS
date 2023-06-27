package by.tms.eshop.service;

import static by.tms.eshop.utils.Constants.AND_SIZE;
import static by.tms.eshop.utils.Constants.Attributes.FOUND_PRODUCTS;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_RESULT_SAVE;
import static by.tms.eshop.utils.Constants.SEARCH_PAGE_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.tms.eshop.dto.ProductDto;
import jakarta.servlet.http.HttpSession;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @Nested
    class ProductsPageBySearchCondition {

        private String searchCondition;
        private final String path = REDIRECT_TO_SEARCH_RESULT_SAVE + AND_SIZE + SEARCH_PAGE_SIZE;

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

/*
 public ModelAndView getSearchFilterResultPagePath(HttpServletRequest request, String category) {
        BigDecimal minPrice = getPrice(request, MIN_PRICE, BigDecimal.ZERO);
        BigDecimal maxPrice = getPrice(request, MAX_PRICE, new BigDecimal(Long.MAX_VALUE));
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession(false);
        if (session.getAttribute(FOUND_PRODUCTS) != null) {
            session.setAttribute(FILTER_FOUND_PRODUCTS, getProductByFilter(session, category, minPrice, maxPrice));
            modelAndView.setViewName(REDIRECT_TO_SEARCH_FILTER_TRUE_RESULT_SAVE + AND_SIZE + SEARCH_PAGE_SIZE);
        } else {
            if (!ALL.equals(category)) {
                session.setAttribute(FOUND_PRODUCTS, productService.selectProductsFromCategoryByFilter(category, minPrice, maxPrice));
            } else {
                session.setAttribute(FOUND_PRODUCTS, productService.selectAllProductsByFilter(minPrice, maxPrice));
            }
            modelAndView.setViewName(REDIRECT_TO_SEARCH_RESULT_SAVE + AND_SIZE + SEARCH_PAGE_SIZE);
        }
        return modelAndView;
    }
 */
    @Nested
    class SearchFilterResultPagePath{

    @Test
    void test_getSearchFilterResultPagePath_foundProductsNotEmpty() {
    }

    @Test
    void test_getSearchFilterResultPagePath_foundProductsEmpty_allCategories() {
    }

    @Test
    void test_getSearchFilterResultPagePath_foundProductsEmpty_currentCategory() {
    }
}

    @Test
    void setPagination() {
    }

    @Test
    void processFilter() {
    }
}