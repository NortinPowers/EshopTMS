package by.tms.eshop.controller;

import static by.tms.eshop.utils.Constants.CATEGORY;
import static by.tms.eshop.utils.Constants.RequestParameters.ID;

import by.tms.eshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;

    @GetMapping("/products-page")
    public ModelAndView showProductsPage(@RequestParam(CATEGORY) String category,
                                         @PageableDefault(sort = ID) Pageable pageable) {
        return productService.getViewProductsByCategory(category, pageable);
    }

    @GetMapping("/product/{id}")
    public ModelAndView showProductPage(@PathVariable(ID) Long id) {
        return productService.getViewProduct(id);
    }
}
