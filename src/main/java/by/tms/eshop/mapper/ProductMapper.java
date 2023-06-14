package by.tms.eshop.mapper;

import by.tms.eshop.domain.Product;
import by.tms.eshop.dto.ProductDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

//    @Mapping(target = "category", source = "product.productCategory.category")
//    ProductDto convertToProductDto(Product product);
//
//    @Mapping(target = "price", source = "price", numberFormat = "#.##E0")
//    @Mapping(source = "productCategory", target = "category")
//    Product convertToProduct(ProductDto product);

    @Mapping(source = "product.productCategory.category", target = "category")
    ProductDto convertToProductDto(Product product);

    @InheritInverseConfiguration
    @Mapping(target = "productCategory", expression = "java(new ProductCategory(dto.getCategory()))")
    Product convertToProduct(ProductDto productDto);

}
