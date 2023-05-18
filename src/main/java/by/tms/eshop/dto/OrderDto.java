package by.tms.eshop.dto;

import by.tms.eshop.domain.Product;
import by.tms.eshop.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class OrderDto {

    private Long id;
    private String name;
    private LocalDate date;
    private User user;
    List<Product> products;
}