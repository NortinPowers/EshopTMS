package by.tms.eshop.repository;

import static by.tms.eshop.test_utils.Constants.PHONE;
import static by.tms.eshop.test_utils.Constants.TEST_PROPERTY_SOURCE_LOCATIONS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import by.tms.eshop.domain.Product;
import java.math.BigDecimal;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = TEST_PROPERTY_SOURCE_LOCATIONS)
@Sql(value = "classpath:sql/product/product-repository-before.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:sql/product/product-repository-after.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private BigDecimal minPrice = BigDecimal.valueOf(450);
    private BigDecimal maxPrice = BigDecimal.valueOf(850);
    private String category = PHONE;
    private String condition;
    private int expectedSize = 2;

    @Nested
    class TestFindAllWithPaginationByProductCategoryToCategory {

        private final Pageable pageable = PageRequest.of(0, 5);
        private Page<Product> productsByCategory;

        @Test
        void test_findAllWithPaginationByProductCategory_Category_isPresent() {
            productsByCategory = productRepository.findAllWithPaginationByProductCategory_Category(category, pageable);

            assertFalse(productsByCategory.getContent().isEmpty());
            assertEquals(expectedSize, productsByCategory.getContent().size());
        }

        @Test
        void test_findAllWithPaginationByProductCategory_Category_isNotPresent() {
            category = "some";

            productsByCategory = productRepository.findAllWithPaginationByProductCategory_Category(category, pageable);

            assertTrue(productsByCategory.getContent().isEmpty());
        }
    }

    @Nested
    class TestGetProductCategoryValue {

        private Long id = 1L;
        private String productCategory;

        @Test
        void test_getProductCategoryValue_isPresent() {
            productCategory = productRepository.getProductCategoryValue(id);

            assertEquals(category, productCategory);
        }

        @Test
        void test_getProductCategoryValue_isNotPresent() {
            id = 0L;

            productCategory = productRepository.getProductCategoryValue(id);

            assertNull(productCategory);
        }
    }

    @Nested
    class TestSelectProductsFromCategoryByFilter {

        @Test
        void test_selectProductsFromCategoryByFilter_isPresent() {
            expectedSize = 1;

            Set<Product> products = productRepository.selectProductsFromCategoryByFilter(category, minPrice, maxPrice);

            assertFalse(products.isEmpty());
            assertEquals(expectedSize, products.size());
        }

        @Test
        void test_selectProductsFromCategoryByFilter_isNotPresent() {
            category = "some";

            Set<Product> products = productRepository.selectProductsFromCategoryByFilter(category, minPrice, maxPrice);

            assertTrue(products.isEmpty());
        }
    }

    @Nested
    class TestSelectAllProductsByFilter {

        @Test
        void test_selectAllProductsByFilter_isPresent() {
            Set<Product> products = productRepository.selectAllProductsByFilter(minPrice, maxPrice);

            assertFalse(products.isEmpty());
            assertEquals(expectedSize, products.size());
        }

        @Test
        void test_selectAllProductsByFilter_isNotPresent() {
            minPrice = BigDecimal.valueOf(1000);
            maxPrice = BigDecimal.valueOf(2000);
            Set<Product> products = productRepository.selectAllProductsByFilter(minPrice, maxPrice);

            assertTrue(products.isEmpty());
        }
    }

    @Nested
    class TestGetProductsByConditionInName {

        @Test
        void test_getProductsByConditionInName_isPresent() {
            condition = "app";
            expectedSize = 1;

            Set<Product> products = productRepository.getProductsByConditionInName(condition);

            assertFalse(products.isEmpty());
            assertEquals(expectedSize, products.size());
        }

        @Test
        void test_getProductsByConditionInName_isNotPresent() {
            condition = "qwe";

            Set<Product> products = productRepository.getProductsByConditionInName(condition);

            assertTrue(products.isEmpty());
        }
    }

    @Nested
    class TestGetProductsByConditionInInfo {

        @Test
        void test_getProductsByConditionInInfo_isPresent() {
            condition = "phone";

            Set<Product> products = productRepository.getProductsByConditionInInfo(condition);

            assertFalse(products.isEmpty());
            assertEquals(expectedSize, products.size());
        }

        @Test
        void test_getProductsByConditionInInfo_isNotPresent() {
            condition = "qwe";

            Set<Product> products = productRepository.getProductsByConditionInInfo(condition);

            assertTrue(products.isEmpty());
        }
    }
}
