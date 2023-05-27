package by.tms.eshop.controller;

import by.tms.eshop.dto.UserFormDto;
import by.tms.eshop.service.ShopFacade;
import by.tms.eshop.validator.ExcludeLogValidation;
import by.tms.eshop.validator.UserValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import static by.tms.eshop.utils.Constants.MappingPath.CREATE_USER;
import static by.tms.eshop.utils.Constants.MappingPath.LOGIN;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_ESHOP;
import static by.tms.eshop.utils.Constants.MappingPath.SUCCESS_REGISTER;
import static by.tms.eshop.utils.ControllerUtils.closeUserSession;
import static by.tms.eshop.utils.ControllerUtils.fillUserValidationError;
import static by.tms.eshop.utils.ControllerUtils.fillsLoginVerifyErrors;
import static by.tms.eshop.utils.ControllerUtils.setViewByAccessPermission;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final UserValidator userValidator;
    private final ShopFacade shopFacade;

    @GetMapping("/login")
    public ModelAndView showLoginPage(HttpSession session,  ModelAndView modelAndView ) {
        setViewByAccessPermission(session, modelAndView);
        return modelAndView;
    }

    @PostMapping("/login-verify")
    public ModelAndView showLoginVerifyPage(HttpServletRequest request,
                                            @Validated(Default.class) @ModelAttribute("user") UserFormDto user,
                                            BindingResult bindingResult,
                                            ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            fillsLoginVerifyErrors(bindingResult, modelAndView);
            modelAndView.setViewName(LOGIN);
        } else {
            shopFacade.checkLoginUser(request, user, modelAndView);
        }
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView showLogoutPage(HttpSession session) {
        closeUserSession(session);
        return new ModelAndView(REDIRECT_TO_ESHOP);
    }

    @GetMapping("/create-user")
    public ModelAndView create() {
        return new ModelAndView(CREATE_USER);
    }

    @PostMapping("/create-user")
    public ModelAndView createUser(HttpServletRequest request,
                                   @Validated({Default.class, ExcludeLogValidation.class}) @ModelAttribute("user") UserFormDto user,
                                   BindingResult bindingResult,
                                   ModelAndView modelAndView) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            fillUserValidationError(bindingResult, modelAndView);
            modelAndView.setViewName(CREATE_USER);
        } else {
            shopFacade.createAndLoginUser(request, user);
            modelAndView.setViewName(SUCCESS_REGISTER);
        }
        return modelAndView;
    }
}