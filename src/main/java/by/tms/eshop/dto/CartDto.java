package by.tms.eshop.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class CartDto {

    private Long id;
    private UserDto userDto;
    private ProductDto productDto;
    private boolean cart;
    private boolean favorite;
    private Integer count;
}
