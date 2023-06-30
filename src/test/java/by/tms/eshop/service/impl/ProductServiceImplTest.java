package by.tms.eshop.service.impl;

import static by.tms.eshop.test_utils.Constants.PHONE;
import static by.tms.eshop.test_utils.Constants.PRODUCT_ID;
import static by.tms.eshop.test_utils.Constants.TV;
import static by.tms.eshop.utils.Constants.Attributes.URL;
import static by.tms.eshop.utils.Constants.MappingPath.PRODUCT;
import static by.tms.eshop.utils.Constants.MappingPath.PRODUCTS;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_ESHOP;
import static by.tms.eshop.utils.Constants.PAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.tms.eshop.domain.Product;
import by.tms.eshop.domain.ProductCategory;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.mapper.ProductMapper;
import by.tms.eshop.repository.ProductRepository;
import by.tms.eshop.service.ProductService;
import by.tms.eshop.utils.Constants.Attributes;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.web.servlet.ModelAndView;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductMapper productMapper;

    private String category = "someCategory";
    private final BigDecimal minPrice = BigDecimal.valueOf(250);
    private final BigDecimal maxPrice = BigDecimal.valueOf(450);
    private final Long productOnePrice = 300L;
    private Product productOne = getProduct(PRODUCT_ID, productOnePrice, TV);
    private ProductDto productDtoOne = getProductDto(PRODUCT_ID, productOnePrice, TV);
    private final Long productTwoId = 2L;
    private final Long productTwoPrice = 500L;
    private Product productTwo = getProduct(productTwoId, productTwoPrice, TV);
    private ProductDto productDtoTwo = getProductDto(productTwoId, productTwoPrice, TV);
    private final Long productThreeId = 3L;
    private final Long productThreePrice = 400L;
    private final Product productThree = getProduct(productThreeId, productThreePrice, PHONE);
    private final ProductDto productDtoThree = getProductDto(productThreeId, productThreePrice, PHONE);
    private ModelAndView modelAndView = new ModelAndView();

    private Product getProduct(Long id, Long price, String category) {
        return Product.builder()
                      .id(id)
                      .price(BigDecimal.valueOf(price))
                      .productCategory(ProductCategory.builder()
                                                      .category(category)
                                                      .build())
                      .build();
    }

    private ProductDto getProductDto(Long id, Long price, String category) {
        return ProductDto.builder()
                         .id(id)
                         .price(BigDecimal.valueOf(price))
                         .category(category)
                         .build();
    }

    @Test
    void test_getProductCategoryValue() {
        when(productRepository.getProductCategoryValue(PRODUCT_ID)).thenReturn(category);

        String foundProductCategory = productService.getProductCategoryValue(PRODUCT_ID);

        assertEquals(category, foundProductCategory);
        verify(productRepository, atLeastOnce()).getProductCategoryValue(PRODUCT_ID);
    }

    @Test
    void test_getFoundedProducts() {
        String condition = "condition";
        Set<Product> productsByConditionInName = Set.of(productOne, productThree);
        Set<Product> productsByConditionInInfo = Set.of(productOne, productTwo);
        Set<ProductDto> result = Set.of(productDtoOne, productDtoThree, productDtoTwo);

        when(productRepository.getProductsByConditionInName(condition)).thenReturn(productsByConditionInName);
        when(productRepository.getProductsByConditionInInfo(condition)).thenReturn(productsByConditionInInfo);
        when(productMapper.convertToProductDto(productOne)).thenReturn(productDtoOne);
        when(productMapper.convertToProductDto(productTwo)).thenReturn(productDtoTwo);
        when(productMapper.convertToProductDto(productThree)).thenReturn(productDtoThree);

        Set<ProductDto> foundProducts = productService.getFoundedProducts(condition);

        assertEquals(result, foundProducts);
        verify(productRepository, atLeastOnce()).getProductsByConditionInName(condition);
        verify(productRepository, atLeastOnce()).getProductsByConditionInInfo(condition);
    }

    @Test
    void test_selectAllProductsByFilter() {
        Set<Product> products = Set.of(productOne, productThree);
        Set<ProductDto> productDtos = Set.of(productDtoOne, productDtoThree);

        when(productRepository.selectAllProductsByFilter(minPrice, maxPrice)).thenReturn(products);
        when(productMapper.convertToProductDto(productOne)).thenReturn(productDtoOne);
        when(productMapper.convertToProductDto(productThree)).thenReturn(productDtoThree);

        Set<ProductDto> foundProducts = productService.selectAllProductsByFilter(minPrice, maxPrice);

        assertEquals(productDtos, foundProducts);
        verify(productRepository, atLeastOnce()).selectAllProductsByFilter(minPrice, maxPrice);
        verify(productMapper, atLeastOnce()).convertToProductDto(productOne);
        verify(productMapper, atLeastOnce()).convertToProductDto(productThree);
    }

    @Test
    void test_selectProductsFromCategoryByFilter() {
        category = TV;
        Set<Product> products = Set.of(productOne);
        Set<ProductDto> productDtos = Set.of(productDtoOne);

        when(productRepository.selectProductsFromCategoryByFilter(category, minPrice, maxPrice)).thenReturn(products);
        when(productMapper.convertToProductDto(productOne)).thenReturn(productDtoOne);

        Set<ProductDto> foundProducts = productService.selectProductsFromCategoryByFilter(category, minPrice, maxPrice);

        assertEquals(productDtos, foundProducts);
        verify(productRepository, atLeastOnce()).selectProductsFromCategoryByFilter(category, minPrice, maxPrice);
        verify(productMapper, atLeastOnce()).convertToProductDto(productOne);
    }

    @Test
    void changePrice() {
        Product productToChange = getProduct(PRODUCT_ID, productOnePrice, TV);
        ProductDto productDto = getProductDto(PRODUCT_ID, 500L, TV);
        Long id = productDto.getId();

        when(productRepository.getReferenceById(id)).thenReturn(productToChange);

        productService.changePrice(productDto);

        assertEquals(productDto.getPrice(), productToChange.getPrice());
        verify(productRepository, atLeastOnce()).getReferenceById(id);
    }

    @Nested
    class TestGetViewProductsByCategory {

        private final Pageable pageable = PageRequest.of(0, 3);

        @Test
        void test_getViewProductsByCategory_pageNotEmpty() {
            productOne = Product.builder().build();
            productTwo = Product.builder().build();
            List<Product> products = List.of(productOne, productTwo, productOne);
            Page<Product> pageProduct = PageableExecutionUtils.getPage(products, pageable, products::size);
            productDtoOne = ProductDto.builder().build();
            productDtoTwo = ProductDto.builder().build();
            List<ProductDto> productDtos = List.of(productDtoOne, productDtoTwo, productDtoOne);
            Page<ProductDto> pageProductDto = PageableExecutionUtils.getPage(productDtos, pageable, productDtos::size);

            when(productRepository.findAllWithPaginationByProductCategory_Category(category, pageable)).thenReturn(pageProduct);
            when(productMapper.convertToProductDto(productOne)).thenReturn(productDtoOne);
            when(productMapper.convertToProductDto(productTwo)).thenReturn(productDtoTwo);

            modelAndView = productService.getViewProductsByCategory(category, pageable);

            assertEquals(pageProductDto, modelAndView.getModelMap().get(PAGE));
            assertEquals("/products-page?category=" + category + "&size=3", modelAndView.getModelMap().get(URL));
            assertEquals(PRODUCTS, modelAndView.getViewName());
        }

        @Test
        void test_getViewProductsByCategory_pageEmpty() {
            when(productRepository.findAllWithPaginationByProductCategory_Category(category, pageable)).thenReturn(Page.empty());
            when(productMapper.convertToProductDto(any(Product.class))).thenReturn(ProductDto.builder().build());

            modelAndView = productService.getViewProductsByCategory(category, pageable);

            assertNull(modelAndView.getModelMap().get(PAGE));
            assertNull(modelAndView.getModelMap().get(URL));
            assertEquals(REDIRECT_TO_ESHOP, modelAndView.getViewName());
        }
    }

    @Nested
    class TestGetViewProduct {

        @Test
        void tets_getViewProduct_productIsPresent() {
            when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(productOne));
            when(productMapper.convertToProductDto(productOne)).thenReturn(productDtoOne);

            modelAndView = productService.getViewProduct(PRODUCT_ID);

            assertEquals(productDtoOne, modelAndView.getModelMap().get(Attributes.PRODUCT));
            assertEquals(PRODUCT, modelAndView.getViewName());
        }

        @Test
        void tets_getViewProduct_productIsNotPresent() {
            when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());

            modelAndView = productService.getViewProduct(PRODUCT_ID);

            assertNull(modelAndView.getModelMap().get(Attributes.PRODUCT));
            assertEquals(PRODUCT, modelAndView.getViewName());
        }
    }

    @Nested
    class TestGetProductDtoTest {

        @Test
        void test_getProductDto_isPresent() {
            when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(productOne));
            when(productMapper.convertToProductDto(productOne)).thenReturn(productDtoOne);

            ProductDto foundProductDto = productService.getProductDto(PRODUCT_ID);

            assertEquals(productDtoOne.getId(), foundProductDto.getId());
            verify(productRepository, atLeastOnce()).findById(PRODUCT_ID);
        }

        @Test
        void test_getProductDto_isNotPresent() {
            when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());

            assertThrows(RuntimeException.class, () -> productService.getProductDto(PRODUCT_ID));
        }
    }
}
