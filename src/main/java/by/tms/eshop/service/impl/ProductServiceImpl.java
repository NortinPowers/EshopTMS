package by.tms.eshop.service.impl;

import static by.tms.eshop.utils.Constants.Attributes.PAGE;
import static by.tms.eshop.utils.Constants.Attributes.URL;
import static by.tms.eshop.utils.Constants.MappingPath.PRODUCT;
import static by.tms.eshop.utils.Constants.MappingPath.PRODUCTS;

import by.tms.eshop.domain.Product;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.mapper.ProductMapper;
import by.tms.eshop.repository.ProductRepository;
import by.tms.eshop.service.ProductService;
import by.tms.eshop.utils.Constants.Attributes;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    //    private final Converter converter;
    private final ProductMapper productMapper;

    @Override
    public ModelAndView getProductsByCategory(String category, Pageable pageable) {
        ModelMap modelMap = new ModelMap();
        Page<ProductDto> page = productRepository.findAllWithPaginationByProductCategory_Category(category, pageable)
                                                 .map(productMapper::convertToProductDto);
//                                                 .map(converter::makeProductDtoModelTransfer);
        modelMap.addAttribute(PAGE, page);
        modelMap.addAttribute(URL, "/products-page?category=" + category + "&size=3");
        return new ModelAndView(PRODUCTS, modelMap);
    }

    @Override
//    public ModelAndView getProduct(Long id, String location) {
    public ModelAndView getProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        ModelMap modelMap = null;
        if (productOptional.isPresent()) {
            modelMap = new ModelMap(Attributes.PRODUCT, productMapper.convertToProductDto(productOptional.get()));
//            modelMap = new ModelMap(Attributes.PRODUCT, converter.makeProductDtoModelTransfer(productOptional.get()));
        }
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject(PRODUCT, modelMap);
//        if (location.equals(FAVORITE)) {
//            modelAndView.addObject(FAVORITE, true);
//        }
//        return modelAndView;
        return new ModelAndView(PRODUCT, modelMap);
    }

    @Override
    public String getProductCategoryValue(Long id) {
        return productRepository.getProductCategoryValue(id);
    }

    @Override
    public Set<ProductDto> getFoundedProducts(String condition) {
        Set<Product> products = productRepository.getProductsByConditionInName(condition);
        products.addAll(productRepository.getProductsByConditionInInfo(condition));
        return products.stream()
                       .map(productMapper::convertToProductDto)
                       .collect(Collectors.toSet());
//        return products.stream().map(converter::makeProductDtoModelTransfer).collect(Collectors.toSet());
    }

    @Override
    public Set<ProductDto> selectAllProductsByFilter(BigDecimal minPrice, BigDecimal maxPrice) {
        return getProductDtoSet(productRepository.selectAllProductsByFilter(minPrice, maxPrice));
    }

    @Override
    public Set<ProductDto> selectProductsFromCategoryByFilter(String category, BigDecimal minPrice, BigDecimal maxPrice) {
        return getProductDtoSet(productRepository.selectProductsFromCategoryByFilter(category, minPrice, maxPrice));
    }

    private Set<ProductDto> getProductDtoSet(Set<Product> convertedProducts) {
        Set<ProductDto> products = new LinkedHashSet<>();
        for (Product product : convertedProducts) {
            products.add(productMapper.convertToProductDto(product));
//            products.add(converter.makeProductDtoModelTransfer(product));
        }
        return products;
    }
}
