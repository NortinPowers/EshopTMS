package by.tms.eshop.controller;

import static by.tms.eshop.utils.Constants.Attributes.FAVORITE_PRODUCTS;
import static by.tms.eshop.utils.Constants.MappingPath.FAVORITES;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_FAVORITES;
import static by.tms.eshop.utils.Constants.PAGE;
import static by.tms.eshop.utils.Constants.RequestParameters.ID;
import static by.tms.eshop.utils.Constants.RequestParameters.LOCATION;
import static by.tms.eshop.utils.ControllerUtils.getAuthenticationUserId;

import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.model.Location;
import by.tms.eshop.service.CartService;
import by.tms.eshop.service.ShopFacade;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final CartService cartService;
    private final ShopFacade shopFacade;

    @GetMapping("/favorites")
    public ModelAndView showFavoritesPage() {
        List<ProductDto> products = shopFacade.getFavoriteProducts(getAuthenticationUserId());
        ModelMap modelMap = new ModelMap(FAVORITE_PRODUCTS, products);
        return new ModelAndView(FAVORITES, modelMap);
    }

    @GetMapping("/add-favorite")
    public ModelAndView addProductToFavorite(@RequestParam(name = ID) Long productId,
                                             @RequestParam(name = LOCATION) String location,
                                             @RequestParam(name = PAGE, required = false) Integer page) {
        cartService.addSelectedProduct(getAuthenticationUserId(), productId, Location.FAVORITE);
        return shopFacade.getModelAndViewByParams(productId, location, page);
    }

    @GetMapping("/delete-favorite")
    public ModelAndView deleteProductFromFavorite(@RequestParam(name = ID) Long productId) {
        cartService.deleteProduct(getAuthenticationUserId(), productId, Location.FAVORITE);
        return new ModelAndView(REDIRECT_TO_FAVORITES);
    }
}
