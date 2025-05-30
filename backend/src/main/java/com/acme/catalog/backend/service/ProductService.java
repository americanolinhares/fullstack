package com.acme.catalog.backend.service;

import com.acme.catalog.backend.dto.ProductDto;
import com.acme.catalog.backend.entity.Product;
import com.acme.catalog.backend.exception.AppException;
import com.acme.catalog.backend.mapper.ProductMapper;
import com.acme.catalog.backend.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  public static final String PRODUCT_NOT_FOUND_WITH_ID = "Product not found with id ";
  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  public List<ProductDto> getAllProducts() {

    return productMapper.toProductDtoList(productRepository.findAll());
  }

  public ProductDto getProductById(Long id) {
    Product product =
        productRepository
            .findById(id)
            .orElseThrow(
                () -> new AppException(PRODUCT_NOT_FOUND_WITH_ID + id, HttpStatus.NOT_FOUND));

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
            .orElseThrow(
                () -> new AppException(PRODUCT_NOT_FOUND_WITH_ID + id, HttpStatus.NOT_FOUND));

    ProductDto productDto = productMapper.toProductDto(product);
    productRepository.deleteById(id);

    return productDto;
  }

  public ProductDto updateProduct(Long id, ProductDto productDto) {
    Product product =
        productRepository
            .findById(id)
            .orElseThrow(
                () -> new AppException(PRODUCT_NOT_FOUND_WITH_ID + id, HttpStatus.NOT_FOUND));

    productMapper.updateProduct(product, productDto);
    Product savedProduct = productRepository.save(product);

    return productMapper.toProductDto(savedProduct);
  }
}
