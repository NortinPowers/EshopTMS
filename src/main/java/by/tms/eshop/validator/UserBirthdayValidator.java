package by.tms.eshop.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class UserBirthdayValidator implements ConstraintValidator<UserBirthdayConstraint, LocalDate> {
    @Override
    public boolean isValid(LocalDate contactField, ConstraintValidatorContext constraintValidatorContext) {
        if (contactField != null) {
            return Period.between(contactField, LocalDate.now()).getYears() > 17;
        } else {
            return false;
        }
    }
}