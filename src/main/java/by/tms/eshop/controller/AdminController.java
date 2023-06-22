package by.tms.eshop.controller;

import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.service.CartService;
import by.tms.eshop.service.ProductService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CartService cartService;
    private final ProductService productService;

    @GetMapping
    public ModelAndView showAdminPage(ModelAndView modelAndView) {
//        List<Map<Long, Long>> mostFavorites = cartService.getMostFavorite();
//        List<Map<ProductDto, Long>> productsWithCount = mostFavorites.stream()
//                                                        .map(m -> m.entrySet().stream()
//                                                                   .collect(Collectors.toMap(
//                                                                           e -> productService.getProductDto(e.getKey()),
//                                                                           Entry::getValue)))
//                                                        .toList();

//        Map<Product, Long> mostFavorite = cartService.getMostFavorite();
//        Map<Long, Long> mostFavorite = cartService.getMostFavorite();
//        Map<ProductDto, Long> productsWithCount = new HashMap<>();
//        for (Entry<Long, Long> entry : mostFavorite.entrySet()) {
//            productsWithCount.put(productService.getProductDto(entry.getKey()), entry.getValue());
//        }
//        List<Long> mostFavorite = cartService.getMostFavorite();
//        List<ProductDto> mostFavorite = cartService.getMostFavorite();
//        List<Map<ProductDto, Long>> mostFavorite = cartService.getMostFavorite();
//        modelAndView.addObject("mostFavorite", mostFavorite);
//        modelAndView.addObject("products", productsWithCount);
//        modelAndView.addObject("mostFavorite", mostFavorite);

        List<Map<Long, Long>> mostFavorites = cartService.getMostFavorite();
        List<Map<ProductDto, Long>> productsWithCount = new ArrayList<>();
        for (Map<Long, Long> mostFavorite : mostFavorites) {
            Map<ProductDto, Long> productWithCount = new HashMap<>();
//            productsWithCount.add(new HashMap<>(productService.getProductDto(mostFavorite.get("productId")), mostFavorite.get("count")));
//            productsWithCount.add(longMap.put(productService.getProductDto(mostFavorite.get("productId"), mostFavorite.get("count"));
            productWithCount.put(productService.getProductDto(mostFavorite.get("productId")), mostFavorite.get("count"));
            productsWithCount.add(productWithCount);
        }

//        List<Map<ProductDto, Long>> productsWithCount = mostFavorites.stream()
//                                                                     .map(m -> m.entrySet().stream()
//                                                                                .collect(Collectors.toMap(
//                                                                                        e -> productService.getProductDto(e.getKey()),
//                                                                                        Entry::getValue)))
//                                                                     .toList();
        modelAndView.addObject("products", productsWithCount);
        modelAndView.setViewName("/admin/info");
        return modelAndView;
//        return new ModelAndView("/admin/info");
    }

}
