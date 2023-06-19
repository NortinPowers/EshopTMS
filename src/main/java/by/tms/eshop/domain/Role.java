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
@Table(name = "roles")
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class Role extends BaseEntity {

    private String role;
    @OneToMany(mappedBy = "role")
    private List<User> users;
}
