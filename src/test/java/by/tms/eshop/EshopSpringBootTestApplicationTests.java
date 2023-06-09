package by.tms.eshop;

import static org.assertj.core.api.Assertions.assertThat;

import by.tms.eshop.controller.AccountController;
import by.tms.eshop.controller.CartController;
import by.tms.eshop.controller.ExceptionController;
import by.tms.eshop.controller.FavoriteController;
import by.tms.eshop.controller.HomeController;
import by.tms.eshop.controller.LoginController;
import by.tms.eshop.controller.ProductsController;
import by.tms.eshop.controller.SearchController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EshopSpringBootTestApplicationTests {

    @Autowired AccountController accountController;
    @Autowired CartController cartController;
    @Autowired ExceptionController exceptionController;
    @Autowired FavoriteController favoriteController;
    @Autowired HomeController homeController;
    @Autowired LoginController loginController;
    @Autowired ProductsController productsController;
    @Autowired SearchController searchController;

    @Test
    void contextLoads() {
        assertThat(accountController).isNotNull();
        assertThat(cartController).isNotNull();
        assertThat(exceptionController).isNotNull();
        assertThat(favoriteController).isNotNull();
        assertThat(homeController).isNotNull();
        assertThat(loginController).isNotNull();
        assertThat(productsController).isNotNull();
        assertThat(searchController).isNotNull();
    }

}
