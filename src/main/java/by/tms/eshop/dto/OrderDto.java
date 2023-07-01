package by.tms.eshop.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class OrderDto {

    private Long id;
    private String name;
    private LocalDate date;
//    private User user;
    private UserDto userDto;
//    private List<Product> products;
    private List<ProductDto> productDtos;

    public List<ProductDto> getProductDtos() {
        return productDtos == null ? new ArrayList<>() : productDtos;
    }
}
