package by.tms.eshop.service;

import static by.tms.eshop.utils.Constants.ALL;
import static by.tms.eshop.utils.Constants.AND_SIZE_5;
import static by.tms.eshop.utils.Constants.Attributes.FILTER_FOUND_PRODUCTS;
import static by.tms.eshop.utils.Constants.Attributes.FOUND_PRODUCTS;
import static by.tms.eshop.utils.Constants.Attributes.URL;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_FILTER_TRUE_RESULT_SAVE;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_RESULT_SAVE;
import static by.tms.eshop.utils.Constants.PAGE;
import static by.tms.eshop.utils.Constants.RequestParameters.FILTER;
import static by.tms.eshop.utils.Constants.RequestParameters.MAX_PRICE;
import static by.tms.eshop.utils.Constants.RequestParameters.MIN_PRICE;
import static by.tms.eshop.utils.Constants.SAVE;
import static by.tms.eshop.utils.Constants.TRUE;

import by.tms.eshop.dto.ProductDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class SearchFacade {

    private final ProductService productService;

    public ModelAndView getProductsPageBySearchCondition(HttpSession session, String searchCondition) {
        if (!searchCondition.isEmpty()) {
            Set<ProductDto> products = productService.getFoundedProducts(searchCondition);
            session.setAttribute(FOUND_PRODUCTS, products);
        }
        return new ModelAndView(REDIRECT_TO_SEARCH_RESULT_SAVE + AND_SIZE_5);
    }

    public ModelAndView getSearchFilterResultPagePath(HttpServletRequest request, String category) {
        BigDecimal minPrice = getPrice(request, MIN_PRICE, BigDecimal.ZERO);
        BigDecimal maxPrice = getPrice(request, MAX_PRICE, new BigDecimal(Long.MAX_VALUE));
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession(false);
        if (session.getAttribute(FOUND_PRODUCTS) != null) {
            session.setAttribute(FILTER_FOUND_PRODUCTS, getProductByFilter(session, category, minPrice, maxPrice));
            modelAndView.setViewName(REDIRECT_TO_SEARCH_FILTER_TRUE_RESULT_SAVE + AND_SIZE_5);
        } else {
            if (!ALL.equals(category)) {
                session.setAttribute(FOUND_PRODUCTS, productService.selectProductsFromCategoryByFilter(category, minPrice, maxPrice));
            } else {
                session.setAttribute(FOUND_PRODUCTS, productService.selectAllProductsByFilter(minPrice, maxPrice));
            }
            modelAndView.setViewName(REDIRECT_TO_SEARCH_RESULT_SAVE + AND_SIZE_5);
        }
        return modelAndView;
    }

    public void setPagination(HttpSession session, Pageable pageable, ModelAndView modelAndView) {
        Set<ProductDto> foundProducts = (Set<ProductDto>) session.getAttribute(FOUND_PRODUCTS);
        Set<ProductDto> filterFoundProducts = (Set<ProductDto>) session.getAttribute(FILTER_FOUND_PRODUCTS);
        if (foundProducts != null || filterFoundProducts != null) {
            List<ProductDto> products = selectSet(foundProducts, filterFoundProducts);
            if (products.size() > 0) {
                int startIndex = pageable.getPageNumber() * pageable.getPageSize();
                int endIndex;
                endIndex = getEndIndex(pageable, products, startIndex);
                Page<ProductDto> page = PageableExecutionUtils.getPage(products.subList(startIndex, endIndex), PageRequest.of(pageable.getPageNumber(), 5), products::size);
                modelAndView.addObject(PAGE, page);
                modelAndView.addObject(URL, "/search?result=save&size=5&filter=true");
            } else {
                Page<ProductDto> page = new PageImpl<>(Collections.emptyList());
                modelAndView.addObject("page", page);
            }
        }
    }

    public void processFilter(HttpSession session, String result, String filter) {
        removeUnsavedAttribute(session, result);
        session.removeAttribute(FILTER);
        setFilterAttribute(session, filter);
    }

    private Set<ProductDto> getProductByFilter(HttpSession session, String type, BigDecimal minPrice, BigDecimal maxPrice) {
        Set<ProductDto> products;
        products = (Set<ProductDto>) session.getAttribute(FOUND_PRODUCTS);
        products = applyPriceFilterOnProducts(minPrice, maxPrice, products);
        products = applyTypeFilterOnProducts(type, products);
        return products;
    }

    private List<ProductDto> selectSet(Set<ProductDto> foundProducts, Set<ProductDto> filterFoundProducts) {
        List<ProductDto> products = new ArrayList<>();
        if (filterFoundProducts != null) {
            products = new ArrayList<>(filterFoundProducts);
        } else if (foundProducts != null) {
            products = new ArrayList<>(foundProducts);
        }
        return products;
    }

    private int getEndIndex(Pageable pageable, List<ProductDto> products, int startIndex) {
        int endIndex;
        if (startIndex == 0) {
            endIndex = 5;
        } else {
            endIndex = startIndex + pageable.getPageSize();
        }
        if (endIndex > products.size()) {
            endIndex = endIndex - (endIndex - products.size());

        }
        return endIndex;
    }

    private BigDecimal getPrice(HttpServletRequest request, String param, BigDecimal defaultValue) {
        String value = request.getParameter(param);
        return StringUtils.isNotBlank(value) ? new BigDecimal(value) : defaultValue;
    }

    private void setFilterAttribute(HttpSession session, String filter) {
        if (TRUE.equals(filter)) {
            session.setAttribute(FILTER, new Object());
        }
    }

    private void removeUnsavedAttribute(HttpSession session, String filterFlag) {
        if (!SAVE.equals(filterFlag)) {
            session.removeAttribute(FOUND_PRODUCTS);
            session.removeAttribute(FILTER_FOUND_PRODUCTS);
        }
    }

    private Set<ProductDto> applyPriceFilterOnProducts(BigDecimal minPrice, BigDecimal maxPrice, Set<ProductDto> products) {
        return products.stream()
                       .filter(product -> product.getPrice().compareTo(minPrice) > 0 && product.getPrice().compareTo(maxPrice) < 0)
                       .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<ProductDto> applyTypeFilterOnProducts(String type, Set<ProductDto> products) {
        Set<ProductDto> productsByType;
        if (!ALL.equals(type)) {
            productsByType = products.stream()
                                     .filter(product -> product.getCategory().equals(type))
                                     .collect(Collectors.toCollection(LinkedHashSet::new));
        } else {
            productsByType = products;
        }
        return productsByType;
    }

}
