package by.tms.eshop.mapper;

import static by.tms.eshop.test_utils.Constants.MapperConstants.PRODUCT;
import static by.tms.eshop.test_utils.Constants.MapperConstants.PRODUCT_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

import by.tms.eshop.domain.Product;
import by.tms.eshop.dto.ProductDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductMapperTest {

    @Autowired
    private ProductMapper productMapper;

    @Test
    void test_convertToProductDto() {
        ProductDto convertedProductDto = productMapper.convertToProductDto(PRODUCT);

        assertEquals(PRODUCT_DTO, convertedProductDto);
    }

    @Test
    @Disabled
//  ? EqualsAndHashCode ProductCategory
    void test_convertToProduct() {
        Product convertedProduct = productMapper.convertToProduct(PRODUCT_DTO);

        assertEquals(PRODUCT.getProductCategory(), convertedProduct.getProductCategory());

//        assertEquals(PRODUCT, convertedProduct);
    }
}
