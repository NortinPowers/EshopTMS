package by.tms.eshop.controller;

import by.tms.eshop.dto.OrderDto;
import by.tms.eshop.dto.UserDto;
import by.tms.eshop.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.tms.eshop.utils.Constants.Attributes.MODELS;
import static by.tms.eshop.utils.Constants.Attributes.USER_ACCESS_PERMISSION;
import static by.tms.eshop.utils.Constants.Attributes.USER_DTO;
import static by.tms.eshop.utils.Constants.Attributes.USER_ORDER;
import static by.tms.eshop.utils.Constants.MappingPath.ACCOUNT;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final OrderService orderService;

    @GetMapping("/account")
    public ModelAndView showAccountPage(HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute(USER_ACCESS_PERMISSION);
        List<OrderDto> orders = orderService.getOrdersById(userDto.getId());
        Map<String, Object> models = new HashMap<>();
        models.put(USER_DTO, userDto);
        models.put(USER_ORDER, orders);
        return new ModelAndView(ACCOUNT, MODELS, models);
    }
}