package by.tms.eshop.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;
import org.springframework.stereotype.Component;

@Component
public class UserBirthdayValidator implements ConstraintValidator<UserBirthdayConstraint, LocalDate> {

    @Override
    public boolean isValid(LocalDate contactField, ConstraintValidatorContext constraintValidatorContext) {
        return Period.between(contactField, LocalDate.now()).getYears() > 17;
    }
}
