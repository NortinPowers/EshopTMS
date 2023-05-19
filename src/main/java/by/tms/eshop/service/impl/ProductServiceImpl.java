package by.tms.eshop.service.impl;

import by.tms.eshop.domain.Product;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.repository.ProductRepository;
import by.tms.eshop.service.ProductService;
import by.tms.eshop.utils.Constants.Attributes;
import by.tms.eshop.utils.DtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static by.tms.eshop.utils.Constants.Attributes.PAGE;
import static by.tms.eshop.utils.Constants.Attributes.URL;
import static by.tms.eshop.utils.Constants.MappingPath.PRODUCT;
import static by.tms.eshop.utils.Constants.MappingPath.PRODUCTS;
import static by.tms.eshop.utils.DtoUtils.makeProductDtoModelTransfer;
import static by.tms.eshop.utils.ServiceUtils.getProductDtoSet;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ModelAndView getProductsByCategory(String category, Pageable pageable) {
        ModelMap modelMap = new ModelMap();
//        List<Product> allByProductCategoryCategory = productRepository.findAllByProductCategory_Category(category);
//        List<ProductDto> products = getProductsDtosFromProducts(allByProductCategoryCategory);
//        PagedListHolder<ProductDto> pageHolder = getPagedListHolder(pageable, products);
//        Page<ProductDto> page = new PageImpl<>(pageHolder.getPageList(), pageable, products.size());
//        List<Product> products = productRepository.findAllByProductCategory_CategoryWithPagination(category).getContent();
//        Page<ProductDto> page = new PageImpl<>(getProductsDtosFromProducts(products), pageable, products.size());
        Page<ProductDto> page = productRepository.findAllWithPaginationByProductCategory_Category(category, pageable).map(DtoUtils::makeProductDtoModelTransfer);
        modelMap.addAttribute(PAGE, page);
//        modelMap.addAttribute(PAGE, productRepository.getProductsByCategory(category, pageable));
        modelMap.addAttribute(URL, "/products-page?category=" + category + "&size=3");
        return new ModelAndView(PRODUCTS, modelMap);
    }

    @Override
    public ModelAndView getProduct(Long id) {
//        ModelMap modelMap = new ModelMap(Attributes.PRODUCT, makeProductDtoModelTransfer(productRepository.getProduct(id)));
        Optional<Product> productOptional = productRepository.findById(id);
        ModelMap modelMap = null;
        if (productOptional.isPresent()) {
            modelMap = new ModelMap(Attributes.PRODUCT, makeProductDtoModelTransfer(productOptional.get()));
//            ModelMap modelMap = new ModelMap(Attributes.PRODUCT, makeProductDtoModelTransfer(productRepository.findById(id)));
        }
        return new ModelAndView(PRODUCT, modelMap);
    }

    @Override
    public String getProductCategoryValue(Long id) {
        return productRepository.getProductCategoryValue(id);
//        return productRepository.findByProductCategory_Id(id);
    }

    @Override
    public Set<ProductDto> getFoundedProducts(String searchCondition) {
        return getProductDtoSet(productRepository.getFoundedProducts(searchCondition));
    }

    @Override
    public Set<ProductDto> selectAllProductsByFilter(String type, BigDecimal minPrice, BigDecimal maxPrice) {
        return getProductDtoSet(productRepository.selectAllProductsByFilter(type, minPrice, maxPrice));
    }
}