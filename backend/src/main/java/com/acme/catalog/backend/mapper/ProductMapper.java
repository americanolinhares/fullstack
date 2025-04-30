package com.acme.catalog.backend.mapper;

import com.acme.catalog.backend.dto.ProductDto;
import com.acme.catalog.backend.entity.Product;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  @Mapping(target = "id", ignore = true)
  Product toProduct(ProductDto productDto);

  ProductDto toProductDto(Product product);

  List<ProductDto> toProductDtoList(List<Product> products);

  @Mapping(target = "id", ignore = true)
  void updateProduct(@MappingTarget Product product, ProductDto productDto);
}
