package by.tms.eshop;

import static org.assertj.core.api.Assertions.assertThat;

import by.tms.eshop.controller.AccountController;
import by.tms.eshop.controller.AdminController;
import by.tms.eshop.controller.CartController;
import by.tms.eshop.controller.ExceptionController;
import by.tms.eshop.controller.FavoriteController;
import by.tms.eshop.controller.HomeController;
import by.tms.eshop.controller.UserController;
import by.tms.eshop.controller.ProductsController;
import by.tms.eshop.controller.SearchController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EshopSpringBootTestApplicationTest {

    @Autowired AccountController accountController;
    @Autowired CartController cartController;
    @Autowired ExceptionController exceptionController;
    @Autowired FavoriteController favoriteController;
    @Autowired HomeController homeController;
    @Autowired UserController userController;
    @Autowired ProductsController productsController;
    @Autowired SearchController searchController;
    @Autowired AdminController adminController;

    @Test
    void contextLoads_accountController_notNull() {
        assertThat(accountController).isNotNull();
    }

    @Test
    void contextLoads_cartController_notNull() {
        assertThat(cartController).isNotNull();
    }

    @Test
    void contextLoads_exceptionController_notNull() {
        assertThat(exceptionController).isNotNull();
    }

    @Test
    void contextLoads_favoriteController_notNull() {
        assertThat(favoriteController).isNotNull();
    }

    @Test
    void contextLoads_homeController_notNull() {
        assertThat(homeController).isNotNull();
    }

    @Test
    void contextLoads_userController_notNull() {
        assertThat(userController).isNotNull();
    }

    @Test
    void contextLoads_productsController_notNull() {
        assertThat(productsController).isNotNull();
    }

    @Test
    void contextLoads_searchController_notNull() {
        assertThat(searchController).isNotNull();
    }

    @Test
    void contextLoads_adminController_notNull() {
        assertThat(adminController).isNotNull();
    }
}
