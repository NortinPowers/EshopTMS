package by.tms.eshop.mapper;

import by.tms.eshop.domain.Product;
import by.tms.eshop.dto.ProductDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CustomMapperConfig.class)
public interface ProductMapper {

    @Mapping(source = "product.productCategory.category", target = "category")
    ProductDto convertToProductDto(Product product);

    @InheritInverseConfiguration
    @Mapping(target = "productCategory", expression = "java(new ProductCategory(dto.getCategory()))")
    Product convertToProduct(ProductDto productDto);
}
