package by.tms.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "by.tms.eshop.repository")
public class EshopSpringBootTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(EshopSpringBootTestApplication.class, args);
    }
}
