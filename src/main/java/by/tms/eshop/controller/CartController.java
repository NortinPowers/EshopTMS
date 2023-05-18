package by.tms.eshop.controller;

import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.service.CartService;
import by.tms.eshop.service.ShopFacade;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static by.tms.eshop.utils.Constants.Attributes.CART_PRODUCTS;
import static by.tms.eshop.utils.Constants.Attributes.FULL_PRICE;
import static by.tms.eshop.utils.Constants.BUY;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_CART;
import static by.tms.eshop.utils.Constants.MappingPath.SHOPPING_CART;
import static by.tms.eshop.utils.Constants.MappingPath.SUCCESS_BUY;
import static by.tms.eshop.utils.Constants.RequestParameters.ID;
import static by.tms.eshop.utils.Constants.RequestParameters.LOCATION;
import static by.tms.eshop.utils.Constants.RequestParameters.SHOP;
import static by.tms.eshop.utils.ControllerUtils.getUserId;
import static by.tms.eshop.utils.DtoUtils.selectCart;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ShopFacade shopFacade;

    @GetMapping("/cart")
    public ModelAndView showCardPage(HttpSession session, ModelAndView modelAndView) {
        Long userId = getUserId(session);
        List<ImmutablePair<ProductDto, Integer>> cartProducts = cartService.getSelectedProducts(userId, selectCart());
        modelAndView.addObject(CART_PRODUCTS, cartProducts);
        modelAndView.addObject(FULL_PRICE, cartService.getProductsPrice(cartProducts));
        modelAndView.setViewName(SHOPPING_CART);
        return modelAndView;
    }

    @PostMapping("/cart-processing")
    public ModelAndView showCardProcessingPage(HttpSession session,
                                               @RequestParam String buy,
                                               ModelAndView modelAndView) {
        if (buy.equalsIgnoreCase(BUY)) {
            shopFacade.carriesPurchase(getUserId(session));
            modelAndView.setViewName(SUCCESS_BUY);
        } else {
            modelAndView.setViewName(REDIRECT_TO_CART);
        }
        return modelAndView;
    }

    @GetMapping("/add-cart")
    public ModelAndView AddProductToCart(HttpSession session,
                                         @RequestParam(name = ID) Long productId,
                                         @RequestParam(name = SHOP) String shopFlag,
                                         @RequestParam(name = LOCATION) String location) {
        cartService.addSelectedProduct(getUserId(session), productId, selectCart());
        return new ModelAndView(shopFacade.getPathFromAddCartByParameters(productId, shopFlag, location));
    }

    @GetMapping("/delete-cart")
    public ModelAndView deleteProductFromCart(HttpSession session,
                                              @RequestParam(name = ID) Long productId) {
        cartService.deleteProduct(getUserId(session), productId, selectCart());
        return new ModelAndView(REDIRECT_TO_CART);
    }
}