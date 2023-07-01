package by.tms.eshop.mapper;

import by.tms.eshop.domain.Order;
import by.tms.eshop.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CustomMapperConfig.class, uses = ProductMapper.class)
public interface OrderMapper {

    @Mapping(source = "user", target = "userDto")
    @Mapping(source = "products", target = "productDtos")
    OrderDto convertToOrderDto(Order order);

//    default ProductDto productToProductDto(Product product) {
//        if (product == null || product.getProductCategory() == null) {
//            return null;
//        }
//
//        ProductDto productDto = ProductDto.builder().build();
//        productDto.setCategory(product.getProductCategory().getCategory());
//        productDto.setId(product.getId());
//        productDto.setName(product.getName());
//        productDto.setPrice(product.getPrice());
//        productDto.setInfo(product.getInfo());
//        return productDto;
//    }
}
