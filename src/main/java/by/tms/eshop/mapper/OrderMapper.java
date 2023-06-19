package by.tms.eshop.mapper;

import by.tms.eshop.domain.Order;
import by.tms.eshop.dto.OrderDto;
import org.mapstruct.Mapper;

@Mapper(config = CustomMapperConfig.class)
//@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto convertToOrderDto(Order order);
}
