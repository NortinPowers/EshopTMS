package by.tms.eshop.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class ProductDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private String category;
    private String info;
}
