package by.tms.eshop.controller;

import static by.tms.eshop.utils.Constants.Attributes.FAVORITE_PRODUCTS;
import static by.tms.eshop.utils.Constants.MappingPath.FAVORITES;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_FAVORITES;
import static by.tms.eshop.utils.Constants.RequestParameters.ID;
import static by.tms.eshop.utils.Constants.RequestParameters.LOCATION;
import static by.tms.eshop.utils.ControllerUtils.getPathFromAddFavoriteByParameters;
import static by.tms.eshop.utils.ControllerUtils.getUserId;

import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.dto.conversion.Convertor;
import by.tms.eshop.service.CartService;
import by.tms.eshop.service.ProductService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final CartService cartService;
    private final ProductService productService;
    private final Convertor convertor;

    @GetMapping("/favorites")
    public ModelAndView showFavoritesPage(HttpSession session) {
        List<ProductDto> productDtos = cartService.getSelectedProducts(getUserId(session), convertor.selectFavorite()).stream()
                                              .map(Pair::getLeft)
                                              .collect(Collectors.toList());
        ModelMap modelMap = new ModelMap(FAVORITE_PRODUCTS, productDtos);
        return new ModelAndView(FAVORITES, modelMap);
    }

    @GetMapping("/add-favorite")
    public ModelAndView addProductToFavorite(HttpSession session,
                                             @RequestParam(name = ID) Long productId,
                                             @RequestParam(name = LOCATION) String location) {
        cartService.addSelectedProduct(getUserId(session), productId, convertor.selectFavorite());
        return new ModelAndView(getPathFromAddFavoriteByParameters(productId, location, productService.getProductCategoryValue(productId)));
    }

    @GetMapping("/delete-favorite")
    public ModelAndView deleteProductFromFavorite(HttpSession session,
                                                  @RequestParam(name = ID) Long productId) {
        cartService.deleteProduct(getUserId(session), productId, convertor.selectFavorite());
        return new ModelAndView(REDIRECT_TO_FAVORITES);
    }
}
