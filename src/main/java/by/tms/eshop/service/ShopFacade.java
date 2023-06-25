package by.tms.eshop.service;

import static by.tms.eshop.utils.Constants.AND_SIZE;
import static by.tms.eshop.utils.Constants.Attributes.ERROR;
import static by.tms.eshop.utils.Constants.Attributes.PRODUCTS;
import static by.tms.eshop.utils.Constants.Attributes.PRODUCT_CATEGORIES;
import static by.tms.eshop.utils.Constants.Attributes.SUCCESS;
import static by.tms.eshop.utils.Constants.Attributes.USER;
import static by.tms.eshop.utils.Constants.BUY;
import static by.tms.eshop.utils.Constants.COUNT;
import static by.tms.eshop.utils.Constants.MappingPath.ACCOUNT;
import static by.tms.eshop.utils.Constants.MappingPath.ADMIN_INFO;
import static by.tms.eshop.utils.Constants.MappingPath.EDIT;
import static by.tms.eshop.utils.Constants.MappingPath.ESHOP;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_CART;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_FAVORITES;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCT_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_RESULT_SAVE;
import static by.tms.eshop.utils.Constants.MappingPath.SUCCESS_BUY;
import static by.tms.eshop.utils.Constants.PRODUCT_ID;
import static by.tms.eshop.utils.Constants.RequestParameters.FAVORITE;
import static by.tms.eshop.utils.Constants.RequestParameters.PRODUCT_PAGE;
import static by.tms.eshop.utils.Constants.RequestParameters.SEARCH;
import static by.tms.eshop.utils.Constants.SIZE_3;
import static by.tms.eshop.utils.Constants.TRUE;
import static by.tms.eshop.utils.ControllerUtils.getAuthenticationUser;
import static by.tms.eshop.utils.ControllerUtils.getAuthenticationUserId;
import static by.tms.eshop.utils.ControllerUtils.getPathByPagination;
import static by.tms.eshop.utils.ControllerUtils.getPathFromAddFavoriteByParameters;

import by.tms.eshop.domain.User;
import by.tms.eshop.dto.CartDto;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.dto.RoleDto;
import by.tms.eshop.dto.UserFormDto;
import by.tms.eshop.mapper.UserMapper;
import by.tms.eshop.model.Location;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Component
@RequiredArgsConstructor
public class ShopFacade {

    private final CartService cartService;
    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final ProductCategoryService productCategoryService;

    public void carriesPurchase(Long userId) {
        List<ProductDto> productsDto = cartService.getPurchasedProducts(userId, Location.CART);
        orderService.saveUserOrder(userId, productsDto);
        cartService.deleteCartProductsAfterBuy(userId);
    }

    public String getPathFromAddCartByParameters(Long productId, String shopFlag, String location, Integer page) {
        String path;
        if (Objects.equals(shopFlag, TRUE)) {
            path = REDIRECT_TO_CART;
        } else if (Objects.equals(location, FAVORITE)) {
            path = REDIRECT_TO_FAVORITES;
        } else if (Objects.equals(location, SEARCH)) {
            path = REDIRECT_TO_SEARCH_RESULT_SAVE;
        } else if (Objects.equals(location, PRODUCT_PAGE)) {
            path = REDIRECT_TO_PRODUCT_WITH_PARAM + productId;
        } else {
            String productCategory = productService.getProductCategoryValue(productId);
            path = REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + productCategory + AND_SIZE + SIZE_3;
        }
        return getPathByPagination(page, path);
    }

    public ModelAndView getModelAndViewByParams(Long productId, String location, Integer page) {
        return new ModelAndView(getPathFromAddFavoriteByParameters(productId, location, productService.getProductCategoryValue(productId), page));
    }

    public ModelAndView getPageByParam(String param, ModelAndView modelAndView) {
        if (param.equalsIgnoreCase(BUY)) {
            carriesPurchase(getAuthenticationUserId());
            modelAndView.setViewName(SUCCESS_BUY);
        } else {
            modelAndView.setViewName(REDIRECT_TO_CART);
        }
        return modelAndView;
    }

    public void getEshopView(ModelAndView modelAndView) {
        List<String> productCategories = productCategoryService.getProductCategories();
        modelAndView.addObject(PRODUCT_CATEGORIES, productCategories);
        modelAndView.setViewName(ESHOP);
    }

    public void createUser(UserFormDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        RoleDto roleUser = roleService.getRole("ROLE_USER");
        user.setRoleDto(roleUser);
        User userEntity = userMapper.convetrToUser(user);
        userService.addUser(userEntity);
    }

    public void editUser(UserFormDto user) {
        User authenticationUser = getAuthenticationUser();
        authenticationUser.setName(user.getName());
        authenticationUser.setSurname(user.getSurname());
        userService.addUser(authenticationUser);
    }

    public List<ProductDto> getFavoriteProducts(Long id) {
        List<CartDto> cartDtos = cartService.getSelectedProducts(id, Location.FAVORITE);
        return cartDtos.stream()
                       .map(CartDto::getProductDto)
                       .collect(Collectors.toList());
    }

    public void getUserEditForm(Long id, ModelAndView modelAndView) {
        if (userService.getUserById(id).isPresent()) {
            User user = userService.getUserById(id).get();
            UserFormDto userFormDto = userMapper.convetrToUserFormDto(user);
            modelAndView.addObject(USER, userFormDto);
            modelAndView.setViewName(EDIT);
        } else {
            modelAndView.setViewName(ACCOUNT);
        }
    }

    public ModelAndView getAdminPage(ModelAndView modelAndView) {
        List<Map<Long, Long>> mostFavorites = cartService.getMostFavorite();
        List<Map<ProductDto, Long>> productsWithCount = mostFavorites.stream()
                                                                     .map(mostFavorite -> {
                                                                         Map<ProductDto, Long> productWithCount = new HashMap<>();
                                                                         productWithCount.put(productService.getProductDto(mostFavorite.get(PRODUCT_ID)), mostFavorite.get(COUNT));
                                                                         return productWithCount;
                                                                     })
                                                                     .toList();
        modelAndView.addObject(PRODUCTS, productsWithCount);
        modelAndView.setViewName(ADMIN_INFO);
        return modelAndView;
    }

    public void setPriceAndRedirectAttributes(ProductDto product, RedirectAttributes attr) {
        boolean isValidPrice = isValidPrice(product);
        addRedirectAttribute(attr, isValidPrice);
        changePriceIfValid(product, isValidPrice);
    }

    private void changePriceIfValid(ProductDto product, boolean isValidPrice) {
        if (isValidPrice) {
            productService.changePrice(product);
        }
    }

    private void addRedirectAttribute(RedirectAttributes attr, boolean condition) {
        if (condition) {
            attr.addFlashAttribute(SUCCESS, true);
        } else {
            attr.addFlashAttribute(ERROR, true);
        }
    }

    private boolean isValidPrice(ProductDto product) {
        return product.getPrice().compareTo(BigDecimal.ZERO) > 0;
    }
}
