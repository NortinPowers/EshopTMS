package by.tms.eshop.mapper;

import by.tms.eshop.domain.Cart;
import by.tms.eshop.dto.CartDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CustomMapperConfig.class)
public interface CartMapper {

    @Mapping(source = "product", target = "productDto")
    @Mapping(source = "product.productCategory.category", target = "productDto.category")
    CartDto convertToCartDto(Cart cart);
}
