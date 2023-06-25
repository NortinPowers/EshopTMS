package by.tms.eshop.controller;

import static by.tms.eshop.utils.Constants.Attributes.PRODUCT;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_ADMIN;

import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.service.ShopFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ShopFacade shopFacade;

    @GetMapping
    public ModelAndView showAdminPage(ModelAndView modelAndView) {
        return shopFacade.getAdminPage(modelAndView);
    }

    @PostMapping("/price")
    public ModelAndView changePrice(@ModelAttribute(name = PRODUCT) ProductDto product,
                                    RedirectAttributes attr) {
        shopFacade.setPriceAndRedirectAttributes(product, attr);
        return new ModelAndView(REDIRECT_TO_ADMIN);
    }
}
