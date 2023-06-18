package by.tms.eshop.mapper;

import by.tms.eshop.domain.Cart;
import by.tms.eshop.dto.CartDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper {

    @Mapping(source = "product", target = "productDto")
    @Mapping(source = "product.productCategory.category", target = "productDto.category")
    CartDto convertToCartDto(Cart cart);
}
