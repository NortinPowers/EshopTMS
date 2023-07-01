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
}
