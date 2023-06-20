package by.tms.eshop.controller;

import static by.tms.eshop.utils.Constants.Attributes.PRODUCT_CATEGORIES;
import static by.tms.eshop.utils.Constants.MappingPath.SEARCH_PATH;
import static by.tms.eshop.utils.Constants.RequestParameters.FILTER;
import static by.tms.eshop.utils.Constants.RequestParameters.ID;
import static by.tms.eshop.utils.Constants.RequestParameters.SEARCH_CONDITION;
import static by.tms.eshop.utils.Constants.RequestParameters.SELECT;

import by.tms.eshop.service.ProductCategoryService;
import by.tms.eshop.service.SearchFacade;
import by.tms.eshop.service.ShopFacade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final ShopFacade shopFacade;
    private final SearchFacade searchFacade;
    private final ProductCategoryService productCategoryService;

    @GetMapping("/search")
    public ModelAndView hasFilterPage(HttpSession session,
                                      @RequestParam(required = false) String result,
                                      @RequestParam(required = false) String filter,
                                      @PageableDefault(sort = ID) Pageable pageable,
                                      ModelAndView modelAndView) {
        searchFacade.processFilter(session, result, filter);
//        ModelMap modelMap = new ModelMap();
//        modelMap.addAttribute(PRODUCT_CATEGORIES, productCategoryService.getProductCategories());
        modelAndView.addObject(PRODUCT_CATEGORIES, productCategoryService.getProductCategories());
        modelAndView.setViewName(SEARCH_PATH);
//        modelAndView.addObject(modelMap);

        searchFacade.setPagination(session, pageable, modelAndView);
//        else {
//            List<ProductDto> emptyList = Collections.emptyList();
//            modelAndView.addObject("emptyList", emptyList);
//        }
//        modelAndView.addObject(modelMap);
        return modelAndView;
    }

//    private void processFilter(HttpSession session, String result, String filter) {
//        searchFacade.removeUnsavedAttribute(session, result);
//        session.removeAttribute(FILTER);
//        searchFacade.setFilterAttribute(session, filter);
//    }

//    private void setPagination(HttpSession session, Pageable pageable, ModelAndView modelAndView) {
//        Set<ProductDto> foundProducts = (Set<ProductDto>) session.getAttribute(FOUND_PRODUCTS);
//        Set<ProductDto> filterFoundProducts = (Set<ProductDto>) session.getAttribute(FILTER_FOUND_PRODUCTS);
////        List<ProductDto> products;
//        if (foundProducts!=null || filterFoundProducts!=null) {
//
//            List<ProductDto> products = new ArrayList<>();
////        if (foundProducts != null) {
//////            if (foundProducts.size() > 0) {
////                products = new ArrayList<>(foundProducts);
//////            modelMap.addAttribute(URL, "redirect:/search?result=save&size=5");
//////            }
////        } else if (filterFoundProducts != null) {
//////            if (filterFoundProducts.size() > 0) {
////                products = new ArrayList<>(filterFoundProducts);
//////            modelMap.addAttribute(URL, "/search?result=save?size=5");
//////            }
////        }
//            if (filterFoundProducts != null) {
//                products = new ArrayList<>(filterFoundProducts);
//            } else if (foundProducts != null) {
//                products = new ArrayList<>(foundProducts);
//            }
////        Page<ProductDto> page;
////        int pageSize = 5;
////        ModelMap modelMap = new ModelMap();
//
//            if (products.size() > 0) {
////        if (products != null) {
//                int startIndex = pageable.getPageNumber() * pageable.getPageSize();
//                int endIndex;
//                if (startIndex == 0) {
//                    endIndex = 5;
//                } else {
//                    endIndex = startIndex + pageable.getPageSize();
//                }
//                if (endIndex > products.size()) {
////               pageSize = endIndex - products.size();
////                endIndex = pageSize + startIndex - 1;
////                endIndex = endIndex - products.size() + startIndex - 1;
//                    endIndex = endIndex - (endIndex - products.size());
//
//                }
////            page = PageableExecutionUtils.getPage(products.subList(startIndex, endIndex), PageRequest.of(pageable.getPageNumber(), 5), products::size);
//                Page<ProductDto> page = PageableExecutionUtils.getPage(products.subList(startIndex, endIndex), PageRequest.of(pageable.getPageNumber(), 5), products::size);
//
//                //            Page<ProductDto> page = PageableExecutionUtils.getPage(products, PageRequest.of(pageable.getPageNumber(), 5), () -> products.size());
////            Page<ProductDto> page = new PageImpl<>(products, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), products.size() / pageable.getPageSize());
//                modelAndView.addObject("page", page);
////            modelMap.addAttribute("page", page);
////            modelMap.addAttribute(URL, "/search?result=save&size=5&filter=true");
//                modelAndView.addObject(URL, "/search?result=save&size=5&filter=true");
////            modelAndView.addObject("modelMap", modelMap);
////            return new ModelAndView("search/search", modelMap);
////            modelAndView.addObject(modelMap);
//            } else {
//                Page<ProductDto> page = new PageImpl<>(Collections.emptyList());
//                modelAndView.addObject("page", page);
//            }
//
//        }
//    }

    @PostMapping("/search-param")
    public ModelAndView showSearchPageByParam(HttpSession session,
                                              @RequestParam(name = SEARCH_CONDITION) String searchCondition) {
        session.removeAttribute(FILTER);
        return searchFacade.getProductsPageBySearchCondition(session, searchCondition);
//        shopFacade.returnProductsBySearchCondition(session, searchCondition);
//        return new ModelAndView(REDIRECT_TO_SEARCH_RESULT_SAVE + "&size=5");
//        return new ModelAndView(REDIRECT_TO_SEARCH_RESULT_SAVE);
    }

    @PostMapping("/search-filter")
    public ModelAndView showSearchPageByFilter(HttpServletRequest request,
                                               @RequestParam(required = false, name = SELECT) String type) {
        return searchFacade.getSearchFilterResultPagePath(request, type);
//        return shopFacade.getSearchFilterResultPagePath(request, type);
    }
}
