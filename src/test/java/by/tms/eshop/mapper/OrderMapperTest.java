package by.tms.eshop.mapper;

import static by.tms.eshop.test_utils.Constants.MapperConstants.USER;
import static by.tms.eshop.test_utils.Constants.MapperConstants.USER_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

import by.tms.eshop.domain.Order;
import by.tms.eshop.dto.OrderDto;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    void test_convertToOrderDto() {
        Long orderId = 1L;
        String orderName = "name";
        LocalDate date = LocalDate.of(2002, 2, 2);
        Order order = Order.builder()
                           .id(orderId)
                           .name(orderName)
                           .date(date)
                           .user(USER)
                           .build();
        OrderDto orderDto = OrderDto.builder()
                                    .id(orderId)
                                    .name(orderName)
                                    .date(date)
                                    .userDto(USER_DTO)
                                    .build();

        OrderDto convertedOrder = orderMapper.convertToOrderDto(order);

        assertEquals(orderDto, convertedOrder);
    }
}
