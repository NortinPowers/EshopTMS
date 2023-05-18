package by.tms.eshop.utils;

import by.tms.eshop.domain.Order;
import by.tms.eshop.domain.Product;
import by.tms.eshop.domain.ProductCategory;
import by.tms.eshop.domain.User;
import by.tms.eshop.dto.LocationDto;
import by.tms.eshop.dto.OrderDto;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.dto.UserDto;
import by.tms.eshop.dto.UserFormDto;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class DtoUtils {

    public static ProductDto makeProductDtoModelTransfer(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .price(product.getPrice())
                .info(product.getInfo())
                .name(product.getName())
                .category(product.getProductCategory().getCategory())
                .build();
    }

    public static Product makeProductModelTransfer(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .price(productDto.getPrice())
                .info(productDto.getInfo())
                .name(productDto.getName())
                .productCategory(ProductCategory.builder()
                        .category(productDto.getCategory())
                        .build())
                .build();
    }

    public static UserDto makeUserDtoModelTransfer(User user) {
        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .birthday(user.getBirthday())
                .build();
    }

    public static User makeUserModelTransfer(UserFormDto user) {
        return User.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .birthday(user.getBirthday())
                .build();
    }

    public static List<Product> getProductsFromProductsDtos(List<ProductDto> productsDto) {
        return productsDto.stream()
                .map(DtoUtils::makeProductModelTransfer)
                .toList();
    }

    public static LocationDto selectCart() {
        return LocationDto.builder()
                .cart(true)
                .favorite(false)
                .build();
    }

    public static LocationDto selectFavorite() {
        return LocationDto.builder()
                .cart(false)
                .favorite(true)
                .build();
    }

    public static OrderDto makeOrderDtoModelTransfer(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .name(order.getName())
                .date(order.getDate())
                .user(order.getUser())
                .products(order.getProducts())
                .build();
    }

    public static List<OrderDto> getOrdersDtosFromOrders(List<Order> orders) {
        return orders.stream()
                .map(DtoUtils::makeOrderDtoModelTransfer)
                .collect(Collectors.toList());
    }
}