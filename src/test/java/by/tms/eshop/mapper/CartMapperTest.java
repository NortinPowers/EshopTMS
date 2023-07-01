package by.tms.eshop.mapper;

import static by.tms.eshop.test_utils.Constants.MapperConstants.PRODUCT;
import static by.tms.eshop.test_utils.Constants.MapperConstants.PRODUCT_DTO;
import static by.tms.eshop.test_utils.Constants.MapperConstants.USER;
import static by.tms.eshop.test_utils.Constants.MapperConstants.USER_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

import by.tms.eshop.domain.Cart;
import by.tms.eshop.dto.CartDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CartMapperTest {

    @Autowired
    private CartMapper cartMapper;

    private final Long cartId = 1L;
    private final Integer cartCount = 2;
    private final Cart cart = Cart.builder()
                                  .id(cartId)
                                  .user(USER)
                                  .product(PRODUCT)
                                  .count(cartCount)
                                  .cart(true)
                                  .favorite(false)
                                  .build();
    private final CartDto cartDto = CartDto.builder()
                                           .id(cartId)
                                           .userDto(USER_DTO)
                                           .productDto(PRODUCT_DTO)
                                           .count(cartCount)
                                           .cart(true)
                                           .favorite(false)
                                           .build();

    @Test
    void convertToCartDto() {
        CartDto convertedCart = cartMapper.convertToCartDto(cart);

        assertEquals(cartDto, convertedCart);
    }

    @Test
    void convertToCartDtos() {
//        cartMapper.convertToCartDtos()
    }
}
