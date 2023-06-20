package by.tms.eshop.service;

import static by.tms.eshop.utils.Constants.AND_PAGE;
import static by.tms.eshop.utils.Constants.AND_SIZE;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_CART;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_FAVORITES;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCT_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_RESULT_SAVE;
import static by.tms.eshop.utils.Constants.RequestParameters.FAVORITE;
import static by.tms.eshop.utils.Constants.RequestParameters.PRODUCT_PAGE;
import static by.tms.eshop.utils.Constants.RequestParameters.SEARCH;
import static by.tms.eshop.utils.Constants.RequestParameters.TRUE;
import static by.tms.eshop.utils.Constants.SIZE_3;
import static by.tms.eshop.utils.ControllerUtils.getAuthenticationUser;

import by.tms.eshop.domain.User;
import by.tms.eshop.dto.CartDto;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.dto.RoleDto;
import by.tms.eshop.dto.UserFormDto;
import by.tms.eshop.mapper.UserMapper;
import by.tms.eshop.model.Location;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class ShopFacade {

    private final CartService cartService;
    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;
    //    private final Converter converter;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public void carriesPurchase(Long userId) {
//        List<ProductDto> productsDto = cartService.getPurchasedProducts(userId, converter.selectCart());
        List<ProductDto> productsDto = cartService.getPurchasedProducts(userId, Location.CART);
        orderService.saveUserOrder(userId, productsDto);
        //--
        cartService.deleteCartProductsAfterBuy(userId);
    }

    //    public String getPathFromAddCartByParameters(Long productId, String shopFlag, String location) {
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
//            path = REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + productCategory + "&size=3";
        }
        if (page == null) {
//        if (page<0) {
            return path;
        } else {
            return path + AND_PAGE + page;
//            return path + "&page=" + page;
        }
//        return path;
    }

//    public ModelAndView getSearchFilterResultPagePath(HttpServletRequest request, String category) {
//        BigDecimal minPrice = getPrice(request, MIN_PRICE, BigDecimal.ZERO);
//        BigDecimal maxPrice = getPrice(request, MAX_PRICE, new BigDecimal(Long.MAX_VALUE));
//        ModelAndView modelAndView = new ModelAndView();
//        HttpSession session = request.getSession(false);
//        if (session.getAttribute(FOUND_PRODUCTS) != null) {
//            session.setAttribute(FILTER_FOUND_PRODUCTS, getProductByFilter(session, category, minPrice, maxPrice));
//            modelAndView.setViewName(REDIRECT_TO_SEARCH_FILTER_TRUE_RESULT_SAVE + "&size=5");
////            modelAndView.setViewName(REDIRECT_TO_SEARCH_FILTER_TRUE_RESULT_SAVE);
//        } else {
//            if (!ALL.equals(category)) {
//                session.setAttribute(FOUND_PRODUCTS, productService.selectProductsFromCategoryByFilter(category, minPrice, maxPrice));
//            } else {
//                session.setAttribute(FOUND_PRODUCTS, productService.selectAllProductsByFilter(minPrice, maxPrice));
//            }
//            modelAndView.setViewName(REDIRECT_TO_SEARCH_RESULT_SAVE + "&size=5");
////            modelAndView.setViewName(REDIRECT_TO_SEARCH_RESULT_SAVE);
//        }
//        return modelAndView;
//    }

//    public void returnProductsBySearchCondition(HttpSession session, String searchCondition) {
//        if (!searchCondition.isEmpty()) {
//            Set<ProductDto> products = productService.getFoundedProducts(searchCondition);
//            session.setAttribute(FOUND_PRODUCTS, products);
//        }
//    }

//    public void createAndLoginUser(HttpServletRequest request, UserFormDto user) {
//        User userEntity = convertor.makeUserModelTransfer(user);
//        userService.addUser(userEntity);
//        saveUserSession(request, convertor.makeUserDtoModelTransfer(userEntity));
//    }

    public void createUser(UserFormDto user) {
//    public void createUser(HttpServletRequest request, UserFormDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        RoleDto roleUser = roleService.getRole("ROLE_USER");
        user.setRoleDto(roleUser);
        User userEntity = userMapper.convetrToUser(user);
//        User userEntity = converter.makeUserModelTransfer(user);
        userService.addUser(userEntity);
//        markUserToLog(converter.makeUserDtoModelTransfer(userEntity));
//        markUser(request, converter.makeUserDtoModelTransfer(userEntity));
    }

    public void editUser(UserFormDto user) {
        User authenticationUser = getAuthenticationUser();
        authenticationUser.setName(user.getName());
        authenticationUser.setSurname(user.getSurname());
        userService.addUser(authenticationUser);

//        if (userService.getUserById(updatedUser.getId()).isPresent()) {
//            User user = userService.getUserById(updatedUser.getId()).get();
//            user.setName(updatedUser.getName());
//            user.setSurname(updatedUser.getSurname());
//
//            //update sec!
//
//            userService.addUser(user);
//            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
//            user.setBirthday(updatedUser.getBirthday());
//        }
    }

    public List<ProductDto> getFavoriteProducts(Long userId) {
        List<CartDto> cartDtos = cartService.getSelectedProducts(userId, Location.FAVORITE);
        return cartDtos.stream()
                       .map(CartDto::getProductDto)
                       .collect(Collectors.toList());
    }

    public String defineLocation(String location) {
        String type = "all";
        if (location != null) {
            if (FAVORITE.equals(location)) {
                type = FAVORITE;
            }
        }
        return type;
    }

    public void getUserEditForm(Long id, ModelAndView modelAndView) {
        if (userService.getUserById(id).isPresent()) {
            User user = userService.getUserById(id).get();
            UserFormDto userFormDto = userMapper.convetrToUserFormDto(user);
            modelAndView.addObject("user", userFormDto);
            modelAndView.setViewName("auth/edit");
        } else {
            modelAndView.setViewName("account/account");
        }
    }

//    public void checkLoginUser(HttpServletRequest request, UserFormDto user, ModelAndView modelAndView) {
//        Optional<User> incomingUser = userService.getUserByLogin(user.getLogin());
//        if (incomingUser.isPresent() && isVerifyUser(incomingUser.get(), user.getPassword())) {
//            UserDto userDto = converter.makeUserDtoModelTransfer(incomingUser.get());
//            saveUserSession(request, userDto);
//            modelAndView.setViewName(REDIRECT_TO_ESHOP);
//        } else {
//            modelAndView.addObject(LOGIN_ERROR, RECHECK_DATA);
//            modelAndView.setViewName(LOGIN);
//        }

//    }
//    public void saveUserOldStyle(HttpServletRequest request, User user, ModelAndView modelAndView) {
//        UserDto userDto = convertor.makeUserDtoModelTransfer(user);
//        saveUserSession(request, userDto);
//        modelAndView.setViewName(REDIRECT_TO_ESHOP);

//    }

//    private Set<ProductDto> getProductByFilter(HttpSession session, String type, BigDecimal minPrice, BigDecimal maxPrice) {
//        Set<ProductDto> products;
//        products = (Set<ProductDto>) session.getAttribute(FOUND_PRODUCTS);
//        products = applyPriceFilterOnProducts(minPrice, maxPrice, products);
//        products = applyTypeFilterOnProducts(type, products);
//        return products;
//    }
}
