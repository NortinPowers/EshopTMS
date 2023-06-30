package by.tms.eshop.service.impl;

import static by.tms.eshop.test_utils.Constants.PHONE;
import static by.tms.eshop.test_utils.Constants.TV;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.tms.eshop.repository.ProductCategoryRepository;
import by.tms.eshop.service.ProductCategoryService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryService productCategoryService;

    @MockBean
    private ProductCategoryRepository productCategoryRepository;

    @Test
    void test_getProductCategories() {
        List<String> categories = List.of(TV, PHONE);

        when(productCategoryRepository.findAllCategory()).thenReturn(categories);

        List<String> foundCategories = productCategoryService.getProductCategories();

        assertEquals(categories, foundCategories);
        verify(productCategoryRepository, atLeastOnce()).findAllCategory();
    }
}
