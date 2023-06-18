package by.tms.eshop.controller;

import static by.tms.eshop.utils.Constants.Attributes.FAVORITE_PRODUCTS;
import static by.tms.eshop.utils.Constants.MappingPath.FAVORITES;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_FAVORITES;
import static by.tms.eshop.utils.Constants.RequestParameters.ID;
import static by.tms.eshop.utils.Constants.RequestParameters.LOCATION;
import static by.tms.eshop.utils.ControllerUtils.getAuthenticationUserId;
import static by.tms.eshop.utils.ControllerUtils.getPathFromAddFavoriteByParameters;

import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.model.Location;
import by.tms.eshop.service.CartService;
import by.tms.eshop.service.ProductService;
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
    private final ProductService productService;
    private final ShopFacade shopFacade;
//    private final Converter converter;

    @GetMapping("/favorites")
    public ModelAndView showFavoritesPage() {
//    public ModelAndView showFavoritesPage(HttpSession session) {
//    public ModelAndView showFavoritesPage(HttpSession session, Principal principal) {
//        User user = getAuthenticationUser();
//        Long id = user.getId();
//        CustomUserDetail userPrincipal = (CustomUserDetail) principal;
//        Long id = userPrincipal.getUserId();
//        List<ProductDto> productDtos = cartService.getSelectedProducts(getUserId(session), converter.selectFavorite()).stream()
//        List<ProductDto> products = cartService.getSelectedProducts(getAuthenticationUserId(), converter.selectFavorite()).stream()

//        List<ProductDto> products = cartService.getSelectedProducts(getAuthenticationUserId(), Location.FAVORITE).stream()
//                                               .map(Pair::getLeft)
//                                               .collect(Collectors.toList());
        List<ProductDto> products = shopFacade.getFavoriteProducts(getAuthenticationUserId());
        ModelMap modelMap = new ModelMap(FAVORITE_PRODUCTS, products);
        return new ModelAndView(FAVORITES, modelMap);
    }

//    private List<ProductDto> getFavoriteProducts(Long userId) {
//        List<CartDto> cartDtos = cartService.getSelectedProducts(getAuthenticationUserId(), Location.FAVORITE);
//        List<ProductDto> products = cartDtos.stream()
//                                                    .map(CartDto::getProductDto)
//                                                    .collect(Collectors.toList());
//        return products;
//    }

//    private static User getAuthenticationUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        CustomUserDetail principal = (CustomUserDetail) authentication.getPrincipal();
//        return principal.getUser();
//    }

    @GetMapping("/add-favorite")
//    public ModelAndView addProductToFavorite(HttpSession session,
    public ModelAndView addProductToFavorite(@RequestParam(name = ID) Long productId,
//                                             @RequestParam(name = LOCATION) String location) {
                                             @RequestParam(name = LOCATION) String location,
                                             @RequestParam(name = "page", required = false) Integer page) {
        cartService.addSelectedProduct(getAuthenticationUserId(), productId, Location.FAVORITE);
//        cartService.addSelectedProduct(getAuthenticationUserId(), productId, converter.selectFavorite());
//        cartService.addSelectedProduct(getUserId(session), productId, converter.selectFavorite());
//        if (page == null) {
//            page = -1;
//        }
        return new ModelAndView(getPathFromAddFavoriteByParameters(productId, location, productService.getProductCategoryValue(productId), page));
//        return new ModelAndView(getPathFromAddFavoriteByParameters(productId, location, productService.getProductCategoryValue(productId)));
    }

    @GetMapping("/delete-favorite")
//    public ModelAndView deleteProductFromFavorite(HttpSession session,
    public ModelAndView deleteProductFromFavorite(@RequestParam(name = ID) Long productId) {
        cartService.deleteProduct(getAuthenticationUserId(), productId, Location.FAVORITE);
//        cartService.deleteProduct(getAuthenticationUserId(), productId, converter.selectFavorite());
//        cartService.deleteProduct(getUserId(session), productId, converter.selectFavorite());
        return new ModelAndView(REDIRECT_TO_FAVORITES);
    }
}
