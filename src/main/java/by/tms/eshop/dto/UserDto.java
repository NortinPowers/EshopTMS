package by.tms.eshop.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Builder
public class UserDto {

    private Long id;
    private String login;
    private String name;
    private String surname;
    private String email;
    private LocalDate birthday;
}
