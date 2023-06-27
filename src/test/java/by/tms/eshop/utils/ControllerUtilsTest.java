package by.tms.eshop.utils;

import static by.tms.eshop.utils.ControllerUtils.getProductsPrice;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import by.tms.eshop.dto.CartDto;
import by.tms.eshop.dto.ProductDto;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ControllerUtilsTest {

    private ProductDto productOne;
    private ProductDto productTwo;
    private CartDto countOne;
    private CartDto countTwo;

    @Test
    void test_getProductsPrice_success() {
        productOne = getProduct(BigDecimal.TEN);
        productTwo = getProduct(BigDecimal.TWO);
        countOne = getCart(productOne, 2);
        countTwo = getCart(productTwo, 4);
        List<CartDto> carts = List.of(countOne, countTwo);

        assertEquals(BigDecimal.valueOf(28), getProductsPrice(carts));
    }

    @Test
    void test_getProductsPrice_failure() {
        productOne = getProduct(BigDecimal.ONE);
        productTwo = getProduct(BigDecimal.TWO);
        countOne = getCart(productOne, 1);
        countTwo = getCart(productTwo, 2);
        List<CartDto> carts = List.of(countOne, countTwo);

        assertNotEquals(BigDecimal.valueOf(10), getProductsPrice(carts));
    }

    private CartDto getCart(ProductDto product, Integer count) {
        return CartDto.builder()
                      .productDto(product)
                      .count(count)
                      .build();
    }

    private ProductDto getProduct(BigDecimal price) {
        return ProductDto.builder()
                         .price(price)
                         .build();
    }
}
