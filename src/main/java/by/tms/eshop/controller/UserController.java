package by.tms.eshop.controller;

import static by.tms.eshop.utils.Constants.Attributes.USER;
import static by.tms.eshop.utils.Constants.MappingPath.CREATE_USER;
import static by.tms.eshop.utils.Constants.MappingPath.EDIT;
import static by.tms.eshop.utils.Constants.MappingPath.LOGIN;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_ACCOUNT;
import static by.tms.eshop.utils.Constants.MappingPath.SUCCESS_REGISTER;
import static by.tms.eshop.utils.Constants.RequestParameters.ID;

import by.tms.eshop.dto.UserFormDto;
import by.tms.eshop.service.ShopFacade;
import by.tms.eshop.validator.EditValidator;
import by.tms.eshop.validator.UserValidator;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserValidator userValidator;
    private final ShopFacade shopFacade;

    @GetMapping("/login")
    public ModelAndView showLoginPage(ModelAndView modelAndView) {
        modelAndView.setViewName(LOGIN);
        return modelAndView;
    }

    @GetMapping("/create-user")
    public ModelAndView create(@ModelAttribute(USER) UserFormDto user) {
        return new ModelAndView(CREATE_USER);
    }

    @PostMapping("/create-user")
    public ModelAndView createUser(@Validated({Default.class, EditValidator.class}) @ModelAttribute(USER) UserFormDto user,
                                   BindingResult bindingResult,
                                   ModelAndView modelAndView) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(CREATE_USER);
        } else {
            shopFacade.createUser(user);
            modelAndView.setViewName(SUCCESS_REGISTER);
        }
        return modelAndView;
    }

    @GetMapping("/edit-user/{id}")
    public ModelAndView edit(@PathVariable(ID) Long id,
                             ModelAndView modelAndView) {
        shopFacade.getUserEditForm(id, modelAndView);
        return modelAndView;
    }

    @PostMapping("/edit-user/{id}")
    public ModelAndView editUser(@Validated(EditValidator.class) @ModelAttribute(USER) UserFormDto user,
                                 BindingResult bindingResult,
                                 ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(EDIT);
        } else {
            shopFacade.editUser(user);
            modelAndView.setViewName(REDIRECT_TO_ACCOUNT);
        }
        return modelAndView;
    }
}
