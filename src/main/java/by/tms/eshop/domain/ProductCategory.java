package by.tms.eshop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "product_category")
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class ProductCategory extends BaseEntity {

    private String category;
    @OneToMany(mappedBy = "productCategory")
    private List<Product> product;
}
