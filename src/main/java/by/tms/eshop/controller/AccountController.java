package by.tms.eshop.controller;

import static by.tms.eshop.utils.Constants.Attributes.MODELS;
import static by.tms.eshop.utils.Constants.Attributes.USER_ORDER;
import static by.tms.eshop.utils.Constants.MappingPath.ACCOUNT;
import static by.tms.eshop.utils.ControllerUtils.getAuthenticationUserId;

import by.tms.eshop.dto.OrderDto;
import by.tms.eshop.service.OrderService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final OrderService orderService;
//    private final Converter converter;

//    @PreAuthorize("isAuthenticated()")
    @GetMapping("/account")
    public ModelAndView showAccountPage() {
//    public ModelAndView showAccountPage(HttpSession session, Principal principal) {
//        UserDto userDto = getUserDto(session);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        CustomUserDetail principal = (CustomUserDetail) authentication.getPrincipal();
//        CustomUserDetail userPrincipal = (CustomUserDetail) principal;
//        User user = principal.getUser();
//        User user = getAuthenticationUser();
//        UserDto userDto = converter.makeUserDtoModelTransfer(user);
//        List<OrderDto> orders = orderService.getOrdersById(userDto.getId());
        List<OrderDto> orders = orderService.getOrdersById(getAuthenticationUserId());
        Map<String, Object> models = new HashMap<>();
//        models.put(USER_DTO, userDto);
        models.put(USER_ORDER, orders);
//        models.put("userId", getAuthenticationUserId());
//        User authenticationUser = getAuthenticationUser();
//        models.put("user", authenticationUser);
        return new ModelAndView(ACCOUNT, MODELS, models);
    }
}
