package by.tms.eshop.utils;

import static by.tms.eshop.utils.Constants.UserVerifyField.BIRTHDAY;
import static by.tms.eshop.utils.Constants.UserVerifyField.EMAIL;
import static by.tms.eshop.utils.Constants.UserVerifyField.NAME;
import static by.tms.eshop.utils.Constants.UserVerifyField.PASSWORD;
import static by.tms.eshop.utils.Constants.UserVerifyField.SURNAME;
import static by.tms.eshop.utils.Constants.UserVerifyField.VERIFY_PASSWORD;

import by.tms.eshop.utils.Constants.UserVerifyField;
import lombok.experimental.UtilityClass;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

@UtilityClass
public class ValidatorUtils {

    public static void fillUserValidationError(BindingResult bindingResult, ModelAndView modelAndView) {
        fillError(UserVerifyField.LOGIN, modelAndView, bindingResult);
        fillError(PASSWORD, modelAndView, bindingResult);
        fillError(VERIFY_PASSWORD, modelAndView, bindingResult);
        fillError(NAME, modelAndView, bindingResult);
        fillError(SURNAME, modelAndView, bindingResult);
        fillError(EMAIL, modelAndView, bindingResult);
        fillError(BIRTHDAY, modelAndView, bindingResult);
    }

    public static void fillsEditVerifyErrors(BindingResult bindingResult, ModelAndView modelAndView) {
        fillError(PASSWORD, modelAndView, bindingResult);
        fillError(VERIFY_PASSWORD, modelAndView, bindingResult);
        fillError(NAME, modelAndView, bindingResult);
        fillError(SURNAME, modelAndView, bindingResult);
        fillError(BIRTHDAY, modelAndView, bindingResult);
    }

    private void fillError(String field, ModelAndView modelAndView, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors(field)) {
            FieldError fieldError = bindingResult.getFieldError(field);
            if (fieldError != null) {
                modelAndView.addObject(field + "Error", fieldError.getDefaultMessage());
            }
        }
    }

}
