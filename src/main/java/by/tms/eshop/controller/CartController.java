package by.tms.eshop.controller;

import static by.tms.eshop.utils.Constants.Attributes.CART_PRODUCTS;
import static by.tms.eshop.utils.Constants.Attributes.FULL_PRICE;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_CART;
import static by.tms.eshop.utils.Constants.MappingPath.SHOPPING_CART;
import static by.tms.eshop.utils.Constants.PAGE;
import static by.tms.eshop.utils.Constants.RequestParameters.ID;
import static by.tms.eshop.utils.Constants.RequestParameters.LOCATION;
import static by.tms.eshop.utils.Constants.RequestParameters.SHOP;
import static by.tms.eshop.utils.ControllerUtils.getAuthenticationUserId;
import static by.tms.eshop.utils.ControllerUtils.getProductsPrice;

import by.tms.eshop.dto.CartDto;
import by.tms.eshop.model.Location;
import by.tms.eshop.service.CartService;
import by.tms.eshop.service.ShopFacade;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ShopFacade shopFacade;

    @GetMapping("/cart")
    public ModelAndView showCardPage(ModelAndView modelAndView) {
        List<CartDto> cartProducts = cartService.getSelectedProducts(getAuthenticationUserId(), Location.CART);
        modelAndView.addObject(CART_PRODUCTS, cartProducts);
        modelAndView.addObject(FULL_PRICE, getProductsPrice(cartProducts));
        modelAndView.setViewName(SHOPPING_CART);
        return modelAndView;
    }

    @PostMapping("/cart-processing")
    public ModelAndView showCartProcessingPage(@RequestParam String buy,
                                               ModelAndView modelAndView) {
        return shopFacade.getPageByParam(buy, modelAndView);
    }

    @GetMapping("/add-cart")
    public ModelAndView addProductToCart(@RequestParam(name = ID) Long productId,
                                         @RequestParam(name = SHOP) String shopFlag,
                                         @RequestParam(name = LOCATION) String location,
                                         @RequestParam(name = PAGE, required = false) Integer page) {
        cartService.addSelectedProduct(getAuthenticationUserId(), productId, Location.CART);
        return new ModelAndView(shopFacade.getPathFromAddCartByParameters(productId, shopFlag, location, page));
    }

    @GetMapping("/delete-cart")
    public ModelAndView deleteProductFromCart(@RequestParam(name = ID) Long productId) {
        cartService.deleteProduct(getAuthenticationUserId(), productId, Location.CART);
        return new ModelAndView(REDIRECT_TO_CART);
    }
}
