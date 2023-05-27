package by.tms.eshop.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class ProductDto implements Serializable {

    private Long id;
    private String name;
    private BigDecimal price;
    private String category;
    private String info;
}