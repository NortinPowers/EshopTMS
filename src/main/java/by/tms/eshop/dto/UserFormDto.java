package by.tms.eshop.dto;

import static by.tms.eshop.utils.Constants.AUTHORIZATION_PATTERN;
import static by.tms.eshop.utils.Constants.NAMES_PATTERN;

import by.tms.eshop.validator.EditValidator;
import by.tms.eshop.validator.UserBirthdayConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class UserFormDto {

    private Long id;
    @NotBlank(groups = Default.class, message = "Enter password")
    @Pattern(groups = Default.class, regexp = AUTHORIZATION_PATTERN, message = "Incorrect password")
    private String password;
    @NotBlank(groups = Default.class, message = "Enter login")
    @Pattern(groups = Default.class, regexp = AUTHORIZATION_PATTERN, message = "Incorrect login")
    private String login;
    @NotBlank(groups = EditValidator.class, message = "Enter name")
    @Pattern(groups = EditValidator.class, regexp = NAMES_PATTERN, message = "Incorrect name")
    private String name;
    @NotBlank(groups = EditValidator.class, message = "Enter surname")
    @Pattern(groups = EditValidator.class, regexp = NAMES_PATTERN, message = "Incorrect surname")
    private String surname;
    @Email(groups = Default.class, message = "Incorrect email")
    private String email;
    @UserBirthdayConstraint(groups = Default.class)
    @NotNull(groups = Default.class)
    private LocalDate birthday;
    private String verifyPassword;
    private RoleDto roleDto;
}
