package by.tms.eshop.dto;

import by.tms.eshop.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartDto {

    private Long id;
    private User user;
    private ProductDto productDto;
    private boolean cart;
    private boolean favorite;
    private Integer count;
}
