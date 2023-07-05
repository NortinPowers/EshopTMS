package by.tms.eshop.controller;

import static by.tms.eshop.utils.Constants.Attributes.USER_ORDER;
import static by.tms.eshop.utils.Constants.MappingPath.ACCOUNT;
import static by.tms.eshop.utils.ControllerUtils.getAuthenticationUserId;

import by.tms.eshop.dto.OrderDto;
import by.tms.eshop.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final OrderService orderService;

    @GetMapping("/account")
    public ModelAndView showAccountPage(ModelAndView modelAndView) {
        List<OrderDto> orders = orderService.getOrdersById(getAuthenticationUserId());
        modelAndView.addObject(USER_ORDER, orders);
        modelAndView.setViewName(ACCOUNT);
        return modelAndView;
    }
}
