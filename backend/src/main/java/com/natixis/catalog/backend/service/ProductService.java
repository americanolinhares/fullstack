package com.natixis.catalog.backend.service;

import com.natixis.catalog.backend.dto.ProductDto;
import com.natixis.catalog.backend.entity.Product;
import com.natixis.catalog.backend.exception.AppException;
import com.natixis.catalog.backend.mapper.ProductMapper;
import com.natixis.catalog.backend.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  public static final String PRODUCT_NOT_FOUND = "Product not found";
  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  public List<ProductDto> getProducts() {
    return productMapper.toProductDtoList(productRepository.findAll());
  }

  public ProductDto getProductById(Long id) {
    Product product =
        productRepository
            .findById(id)
            .orElseThrow(() -> new AppException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));

    return productMapper.toProductDto(product);
  }

  public ProductDto createProduct(ProductDto productDto) {
    Product product = productMapper.toProduct(productDto);
    Product savedProduct = productRepository.save(product);
    return productMapper.toProductDto(savedProduct);
  }

  public ProductDto deleteProduct(Long id) {
    Product product =
        productRepository
            .findById(id)
            .orElseThrow(() -> new AppException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));

    ProductDto productDto = productMapper.toProductDto(product);

    productRepository.deleteById(id);

    return productDto;
  }

  public ProductDto updateProduct(Long id, ProductDto productDto) {
    Product product =
        productRepository
            .findById(id)
            .orElseThrow(() -> new AppException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));

    productMapper.updateProduct(product, productDto);

    Product savedProduct = productRepository.save(product);

    return productMapper.toProductDto(savedProduct);
  }
}
