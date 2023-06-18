package by.tms.eshop.controller;

import static by.tms.eshop.utils.Constants.Attributes.CART_PRODUCTS;
import static by.tms.eshop.utils.Constants.Attributes.FULL_PRICE;
import static by.tms.eshop.utils.Constants.BUY;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_CART;
import static by.tms.eshop.utils.Constants.MappingPath.SHOPPING_CART;
import static by.tms.eshop.utils.Constants.MappingPath.SUCCESS_BUY;
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
//    private final Converter converter;

    @GetMapping("/cart")
    public ModelAndView showCardPage(ModelAndView modelAndView) {
//    public ModelAndView showCardPage(HttpSession session, ModelAndView modelAndView) {
//        Long userId = getUserId(session);
//        List<ImmutablePair<ProductDto, Integer>> cartProducts = cartService.getSelectedProducts(userId, converter.selectCart());
//        List<ImmutablePair<ProductDto, Integer>> cartProducts = cartService.getSelectedProducts(getAuthenticationUserId(), converter.selectCart());
        List<CartDto> cartProducts = cartService.getSelectedProducts(getAuthenticationUserId(), Location.CART);
//        List<ImmutablePair<ProductDto, Integer>> cartProducts = cartService.getSelectedProducts(getAuthenticationUserId(), Location.CART);
        modelAndView.addObject(CART_PRODUCTS, cartProducts);
//        modelAndView.addObject(FULL_PRICE, getProductsPrice(cartProducts));
        modelAndView.addObject(FULL_PRICE, getProductsPrice(cartProducts));
        modelAndView.setViewName(SHOPPING_CART);
        return modelAndView;
    }

    @PostMapping("/cart-processing")
//    public ModelAndView showCartProcessingPage(HttpSession session,
    public ModelAndView showCartProcessingPage(@RequestParam String buy,
                                               ModelAndView modelAndView) {
        if (buy.equalsIgnoreCase(BUY)) {
            shopFacade.carriesPurchase(getAuthenticationUserId());
//            shopFacade.carriesPurchase(getUserId(session));
            modelAndView.setViewName(SUCCESS_BUY);
        } else {
            modelAndView.setViewName(REDIRECT_TO_CART);
        }
        return modelAndView;
    }

    @GetMapping("/add-cart")
//    public ModelAndView addProductToCart(HttpSession session,
    public ModelAndView addProductToCart(@RequestParam(name = ID) Long productId,
                                         @RequestParam(name = SHOP) String shopFlag,
                                         @RequestParam(name = LOCATION) String location,
                                         @RequestParam(name = "page", required = false) Integer page) {
        cartService.addSelectedProduct(getAuthenticationUserId(), productId, Location.CART);
//        cartService.addSelectedProduct(getAuthenticationUserId(), productId, converter.selectCart());
//        cartService.addSelectedProduct(getUserId(session), productId, converter.selectCart());
//        if (page == null) {
//            page = -1;
//        }
        return new ModelAndView(shopFacade.getPathFromAddCartByParameters(productId, shopFlag, location, page));
//        return new ModelAndView(shopFacade.getPathFromAddCartByParameters(productId, shopFlag, location));
    }

    @GetMapping("/delete-cart")
//    public ModelAndView deleteProductFromCart(HttpSession session,
    public ModelAndView deleteProductFromCart(@RequestParam(name = ID) Long productId) {
        cartService.deleteProduct(getAuthenticationUserId(), productId, Location.CART);
//        cartService.deleteProduct(getAuthenticationUserId(), productId, converter.selectCart());
//        cartService.deleteProduct(getUserId(session), productId, converter.selectCart());
        return new ModelAndView(REDIRECT_TO_CART);
    }

//    @GetMapping("/add-still-cart")
////    public ModelAndView deleteProductFromCart(HttpSession session,
//    public ModelAndView addStillProductToCart(@RequestParam(name = ID) Long productId) {
//        cartService.addSelectedProduct(getAuthenticationUserId(), productId, converter.selectCart());
//        return new ModelAndView(REDIRECT_TO_CART);
//    }
}
