package by.tms.eshop.controller;

import static by.tms.eshop.utils.Constants.Attributes.MODELS;
import static by.tms.eshop.utils.Constants.Attributes.USER_ORDER;
import static by.tms.eshop.utils.Constants.MappingPath.ACCOUNT;

import by.tms.eshop.domain.User;
import by.tms.eshop.dto.OrderDto;
import by.tms.eshop.dto.UserDto;
import by.tms.eshop.dto.conversion.Converter;
import by.tms.eshop.security.CustomUserDetail;
import by.tms.eshop.service.OrderService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final OrderService orderService;
    private final Converter converter;

    @GetMapping("/account")
    public ModelAndView showAccountPage() {
//    public ModelAndView showAccountPage(HttpSession session, Principal principal) {
//        UserDto userDto = getUserDto(session);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail principal = (CustomUserDetail) authentication.getPrincipal();
//        CustomUserDetail userPrincipal = (CustomUserDetail) principal;
        User user = principal.getUser();
        UserDto userDto = converter.makeUserDtoModelTransfer(user);
        List<OrderDto> orders = orderService.getOrdersById(userDto.getId());
        Map<String, Object> models = new HashMap<>();
//        models.put(USER_DTO, userDto);
        models.put(USER_ORDER, orders);
        return new ModelAndView(ACCOUNT, MODELS, models);
    }
}
