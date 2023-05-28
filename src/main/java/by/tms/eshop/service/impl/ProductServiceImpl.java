package by.tms.eshop.service.impl;

import static by.tms.eshop.utils.Constants.Attributes.PAGE;
import static by.tms.eshop.utils.Constants.Attributes.URL;
import static by.tms.eshop.utils.Constants.MappingPath.PRODUCT;
import static by.tms.eshop.utils.Constants.MappingPath.PRODUCTS;

import by.tms.eshop.domain.Product;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.dto.conversion.Convertor;
import by.tms.eshop.repository.ProductRepository;
import by.tms.eshop.service.ProductService;
import by.tms.eshop.utils.Constants.Attributes;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
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
    private final Convertor convertor;

    @Override
    public ModelAndView getProductsByCategory(String category, Pageable pageable) {
        ModelMap modelMap = new ModelMap();
        Page<ProductDto> page = productRepository.findAllWithPaginationByProductCategory_Category(category, pageable).map(convertor::makeProductDtoModelTransfer);
        modelMap.addAttribute(PAGE, page);
        modelMap.addAttribute(URL, "/products-page?category=" + category + "&size=3");
        return new ModelAndView(PRODUCTS, modelMap);
    }

    @Override
    public ModelAndView getProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        ModelMap modelMap = null;
        if (productOptional.isPresent()) {
            modelMap = new ModelMap(Attributes.PRODUCT, convertor.makeProductDtoModelTransfer(productOptional.get()));
        }
        return new ModelAndView(PRODUCT, modelMap);
    }

    @Override
    public String getProductCategoryValue(Long id) {
        return productRepository.getProductCategoryValue(id);
    }

    @Override
    public Set<ProductDto> getFoundedProducts(String searchCondition) {
        return getProductDtoSet(productRepository.getFoundedProducts(searchCondition));
    }

    @Override
    public Set<ProductDto> selectAllProductsByFilter(String type, BigDecimal minPrice, BigDecimal maxPrice) {
        return getProductDtoSet(productRepository.selectAllProductsByFilter(type, minPrice, maxPrice));
    }

    private Set<ProductDto> getProductDtoSet(Set<Product> convertedProducts) {
        Set<ProductDto> products = new LinkedHashSet<>();
        for (Product product : convertedProducts) {
            products.add(convertor.makeProductDtoModelTransfer(product));
        }
        return products;
    }
}