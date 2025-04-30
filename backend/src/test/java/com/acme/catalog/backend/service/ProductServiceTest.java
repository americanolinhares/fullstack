package com.acme.catalog.backend.service;

import com.acme.catalog.backend.dto.ProductDto;
import com.acme.catalog.backend.entity.Product;
import com.acme.catalog.backend.exception.AppException;
import com.acme.catalog.backend.mapper.ProductMapper;
import com.acme.catalog.backend.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  @Mock
  private ProductRepository productRepository;

  @Mock
  private ProductMapper productMapper;

  @InjectMocks
  private ProductService productService;

  private Product product;
  private ProductDto productDto;

  @BeforeEach
  void setUp() {

    product = Product.builder().id(1L).name("Product name").description("Product description").build();
    productDto = ProductDto.builder().id(1L).name("Product name").description("Product description").build();
  }

  @Test
  void whenFindProductByValidId_shouldReturnProduct() {
    //Given
    when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable((product)));
    when(productMapper.toProductDto(ArgumentMatchers.any(Product.class))).thenReturn(productDto);

    //When
    ProductDto foundProduct = productService.getProductById(1L);

    //Then
    assertNotNull(foundProduct);
    assertEquals("Product name", foundProduct.getName());
    verify(productRepository, times(1)).findById(1L);
  }

  @Test
  void whenFindProductByInvalidId_shouldReturnNotFound() {
    //Given
    when(productRepository.findById(1L)).thenReturn(Optional.empty());

    //When Then
    Exception exception = assertThrows(RuntimeException.class, () -> productService.getProductById(1L));
    assertEquals("Product not found with id 1", exception.getMessage());
    verify(productRepository, times(1)).findById(1L);
  }

  @Test
  void whenGetAllProducts_shouldReturnProductList() {
    // Given
    List<Product> products = List.of(
            Product.builder().id(1L).name("Product 1").description("Description 1").build(),
            Product.builder().id(2L).name("Product 2").description("Description 2").build()
    );
    List<ProductDto> productDtos = List.of(
            ProductDto.builder().id(1L).name("Product 1").description("Description 1").build(),
            ProductDto.builder().id(2L).name("Product 2").description("Description 2").build()
    );
    when(productRepository.findAll()).thenReturn(products);
    when(productMapper.toProductDtoList(products)).thenReturn(productDtos);

    // When
    List<ProductDto> result = productService.getAllProducts();

    // Then
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Product 1", result.get(0).getName());
    assertEquals("Product 2", result.get(1).getName());
    verify(productRepository, times(1)).findAll();
    verify(productMapper, times(1)).toProductDtoList(products);
  }

  @Test
  void whenCreateProduct_shouldReturnProduct() {
    //Given
    when(productRepository.save(ArgumentMatchers.any(Product.class))).thenReturn(product);
    when(productMapper.toProduct(ArgumentMatchers.any(ProductDto.class))).thenReturn(product);
    when(productMapper.toProductDto(ArgumentMatchers.any(Product.class))).thenReturn(productDto);

    //When
    ProductDto createdProduct = productService.createProduct(productDto);

    //Then
    assertNotNull(createdProduct);
    assertEquals("Product name", createdProduct.getName());
    verify(productRepository, times(1)).save(product);
  }

  @Test
  void whenUpdateExistingProduct_shouldReturnUpdatedProduct() {
      // Given
      ProductDto newProductDto = ProductDto.builder().id(1L).name("New Product name").description("New Product description").build();
      when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
      doAnswer(invocation -> {
          Product existingProduct = invocation.getArgument(0);
          existingProduct.setName(newProductDto.getName());
          existingProduct.setDescription(newProductDto.getDescription());
          return null;
      }).when(productMapper).updateProduct(any(Product.class), any(ProductDto.class));
      when(productRepository.save(any(Product.class))).thenReturn(product);
      when(productMapper.toProductDto(any(Product.class))).thenReturn(newProductDto);

      // When
      ProductDto updatedProduct = productService.updateProduct(1L, newProductDto);

      // Then
      assertNotNull(updatedProduct);
      assertEquals("New Product name", updatedProduct.getName());
      assertEquals("New Product description", updatedProduct.getDescription());
      verify(productRepository, times(1)).findById(1L);
      verify(productRepository, times(1)).save(product);
      verify(productMapper, times(1)).updateProduct(product, newProductDto);
  }

  @Test
  void whenUpdateNonExistingProduct_shouldThrowNotFoundException() {
    // Given
    when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

    // When Then
    Exception exception = assertThrows(AppException.class, () -> productService.updateProduct(2L, productDto));
    assertEquals("Product not found with id 2", exception.getMessage());
    verify(productRepository, times(1)).findById(2L);
    verify(productRepository, never()).save(any(Product.class));
    verify(productMapper, never()).updateProduct(any(Product.class), any(ProductDto.class));
  }

  @Test
  void whenDeleteExistingProduct_shouldReturnDeletedProduct() {
    // Given
    when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
    when(productMapper.toProductDto(any(Product.class))).thenReturn(productDto);

    // When
    ProductDto deletedProduct = productService.deleteProduct(1L);

    // Then
    assertNotNull(deletedProduct);
    assertEquals("Product name", deletedProduct.getName());
    verify(productRepository, times(1)).findById(1L);
    verify(productRepository, times(1)).deleteById(1L);
  }

  @Test
  void whenDeleteNonExistingProduct_shouldThrowNotFoundException() {
    // Given
    when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

    // When Then
    Exception exception = assertThrows(AppException.class, () -> productService.deleteProduct(2L));
    assertEquals("Product not found with id 2", exception.getMessage());
    verify(productRepository, times(1)).findById(2L);
    verify(productRepository, never()).deleteById(anyLong());
  }

}
