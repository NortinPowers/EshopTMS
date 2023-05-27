package by.tms.eshop.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

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