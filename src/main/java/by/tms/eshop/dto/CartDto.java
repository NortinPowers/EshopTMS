package by.tms.eshop.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartDto {

    private Long id;
    private UserDto userDto;
    private ProductDto productDto;
    private boolean cart;
    private boolean favorite;
    private Integer count;
}
