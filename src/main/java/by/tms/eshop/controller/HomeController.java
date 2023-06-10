package by.tms.eshop.controller;

import static by.tms.eshop.utils.Constants.Attributes.PRODUCT_CATEGORIES;
import static by.tms.eshop.utils.Constants.MappingPath.ESHOP;

import by.tms.eshop.service.ProductCategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final ProductCategoryService productCategoryService;

    @GetMapping(value = {"/", "/eshop"})
    public ModelAndView redirectToEshopPage(ModelAndView modelAndView) {
        getEshopView(modelAndView);

        //user!!
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            CustomUserDetail principal = (CustomUserDetail) authentication.getPrincipal();
//            String login = principal.getUser().getLogin();
//            LocalDate birthday = principal.getUser().getBirthday();
//            System.out.println(login + " date:" + birthday);
//        }

        return modelAndView;
    }

    private void getEshopView(ModelAndView modelAndView) {
        List<String> productCategories = productCategoryService.getProductCategories();
        modelAndView.addObject(PRODUCT_CATEGORIES, productCategories);
        modelAndView.setViewName(ESHOP);
    }

    @PostMapping("/")
    public ModelAndView entersToEshop(ModelAndView modelAndView) {
        getEshopView(modelAndView);
        return modelAndView;
    }
}
