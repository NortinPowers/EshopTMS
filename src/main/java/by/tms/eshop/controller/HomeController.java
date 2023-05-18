package by.tms.eshop.controller;

import by.tms.eshop.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static by.tms.eshop.utils.Constants.Attributes.PRODUCT_CATEGORIES;
import static by.tms.eshop.utils.Constants.MappingPath.ESHOP;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final ProductCategoryService productCategoryService;

    @GetMapping(value = {"/","/eshop"})
    public ModelAndView redirectToEshopPage(ModelAndView modelAndView) {
//        log.info("TEST_INFO");
//        log.error("TEST_ERROR");
        List<String> productCategories = productCategoryService.getProductCategories();
        modelAndView.addObject(PRODUCT_CATEGORIES, productCategories);
        modelAndView.setViewName(ESHOP);
        return modelAndView;
    }
}