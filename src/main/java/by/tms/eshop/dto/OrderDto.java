package by.tms.eshop.dto;

import by.tms.eshop.domain.Product;
import by.tms.eshop.domain.User;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderDto {

    private Long id;
    private String name;
    private LocalDate date;
    private User user;
    private List<Product> products;
}
