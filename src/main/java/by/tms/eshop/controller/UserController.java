package by.tms.eshop.controller;

import static by.tms.eshop.utils.Constants.MappingPath.CREATE_USER;
import static by.tms.eshop.utils.Constants.MappingPath.LOGIN;
import static by.tms.eshop.utils.Constants.MappingPath.SUCCESS_REGISTER;
import static by.tms.eshop.utils.Constants.RequestParameters.ID;
import static by.tms.eshop.utils.ControllerUtils.fillUserValidationError;
import static by.tms.eshop.utils.ControllerUtils.fillsEditVerifyErrors;

import by.tms.eshop.dto.UserFormDto;
import by.tms.eshop.service.ShopFacade;
import by.tms.eshop.service.UserService;
import by.tms.eshop.validator.EditValidation;
import by.tms.eshop.validator.UserValidator;
import jakarta.servlet.http.HttpServletRequest;
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
//@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserValidator userValidator;
    private final ShopFacade shopFacade;
    private final UserService userService;
//    private final AuthenticationManager authenticationManager;
//    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

//    @GetMapping("/login")
//    public ModelAndView showLoginPage(HttpSession session, ModelAndView modelAndView) {
//        setViewByAccessPermission(session, modelAndView);
//        return modelAndView;
//    }

    //new
    @GetMapping("/login")
    public ModelAndView showLoginPage(ModelAndView modelAndView) {
        modelAndView.setViewName(LOGIN);
//        modelAndView.setViewName("login/login");
        return modelAndView;
    }

//    @PostMapping("/login")
//    public ModelAndView showSuccess(ModelAndView modelAndView) {
//        modelAndView.setViewName("home/eshop");
//        return modelAndView;
//    }

//        @PostMapping("/login-verify")
//    @PostMapping("/login")
//    public ModelAndView showLoginVerifyPage(HttpServletRequest request,
//                                            HttpServletResponse response,
//                                            @Validated(Default.class) @ModelAttribute("user") UserFormDto user,
//                                            BindingResult bindingResult,
//                                            ModelAndView modelAndView) {
//        if (bindingResult.hasErrors()) {
//            fillsLoginVerifyErrors(bindingResult, modelAndView);
//            modelAndView.setViewName(LOGIN);
//        } else {
//
//            UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
//                    user.getName(), user.getPassword());
//            Authentication authentication = authenticationManager.authenticate(token);
//            SecurityContext context = createEmptyContext();
//            context.setAuthentication(authentication);
//            setContext(context);
//            securityContextRepository.saveContext(context, request, response);
//
//            User authenticationUser = ControllerUtils.getAuthenticationUser();
////            shopFacade.checkLoginUser(request, user, modelAndView);
//
////            shopFacade.saveUserOldStyle(request, user, modelAndView);
//            ControllerUtils.markUserToLog(authenticationUser);
//            modelAndView.setViewName(REDIRECT_TO_ESHOP);
//        }
//        return modelAndView;
//    }

//    @PostMapping("/logout")
////    @GetMapping("/logout")
//    public ModelAndView showLogoutPage() {
////    public ModelAndView showLogoutPage(HttpSession session) {
////        writeLoggedToLog(session);
//        writeLoggedToLog();
//        //fix
//        return new ModelAndView(REDIRECT_TO_ESHOP);
//    }
//
//    @GetMapping("/logout")
////    @GetMapping("/logout")
//    public ModelAndView doLogout() {
////    public ModelAndView showLogoutPage(HttpSession session) {
////        writeLoggedToLog(session);
//        writeLoggedToLog();
//        //fix
//        return new ModelAndView(REDIRECT_TO_ESHOP);
//    }

    @GetMapping("/create-user")
    public ModelAndView create() {
        return new ModelAndView(CREATE_USER);
    }

    @PostMapping("/create-user")
    public ModelAndView createUser(HttpServletRequest request,
                                   @Validated({Default.class, EditValidation.class}) @ModelAttribute("user") UserFormDto user,
                                   BindingResult bindingResult,
                                   ModelAndView modelAndView) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            fillUserValidationError(bindingResult, modelAndView);
            modelAndView.setViewName(CREATE_USER);
        } else {
            shopFacade.createUser(user);
//            shopFacade.createUser(request, user);
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
//    @PatchMapping("/edit-user/{id}")
    public ModelAndView editUser(@PathVariable(ID) Long id,
                                 @Validated(EditValidation.class) @ModelAttribute("user") UserFormDto user,
                                 BindingResult bindingResult,
                                 ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            fillsEditVerifyErrors(bindingResult, modelAndView);
            modelAndView.setViewName("/edit-user/" + id);
        } else {
            shopFacade.editUser(user);
            modelAndView.setViewName("redirect:/account");
        }
        return modelAndView;
    }

//    private void getUserEditForm(Long id, ModelAndView modelAndView) {
//        if (userService.getUserById(id).isPresent()) {
//            modelAndView.addObject("user", userService.getUserById(id));
//            modelAndView.setViewName("auth/edit");
//        } else {
//            modelAndView.setViewName("account/account");
//        }
//    }
}
