package by.tms.eshop.mapper;

import by.tms.eshop.domain.Cart;
import by.tms.eshop.dto.CartDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CustomMapperConfig.class)
public interface CartMapper {

    @Mapping(source = "user", target = "userDto")
    @Mapping(source = "product", target = "productDto")
    @Mapping(source = "product.productCategory.category", target = "productDto.category")
    CartDto convertToCartDto(Cart cart);

    List<CartDto> convertToCartDtos(List<Cart> carts);
}
