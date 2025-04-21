package com.natixis.catalog.backend.mapper;

import com.natixis.catalog.backend.dto.ProductDto;
import com.natixis.catalog.backend.entity.Product;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    //Product toProduct(ProductDto productDto);
    @Mapping(target = "id", ignore = true) // Ignora o campo 'id' ao converter
    Product toProduct(ProductDto productDto);

    ProductDto toProductDto(Product product);

    List<ProductDto> toProductDtoList(List<Product> products);

    @Mapping(target = "id", ignore = true)
    void updateProduct(@MappingTarget Product product, ProductDto productDto);
}
