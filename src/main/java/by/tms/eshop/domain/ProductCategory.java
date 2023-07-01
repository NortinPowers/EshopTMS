package by.tms.eshop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "product_category")
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)

@ToString
public class ProductCategory extends BaseEntity implements Serializable {

    private String category;
    @OneToMany(mappedBy = "productCategory")
    private List<Product> product;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof ProductCategory that)) {
//            return false;
//        }
//        if (!super.equals(o)) {
//            return false;
//        }
//        return Objects.equals(category, that.category);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(super.hashCode(), category);
//    }
}
