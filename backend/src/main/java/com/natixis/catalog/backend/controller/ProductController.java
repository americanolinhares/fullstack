package com.natixis.catalog.backend.controller;

import com.natixis.catalog.backend.dto.ProductDto;
import com.natixis.catalog.backend.service.ProductService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping("/product")
  public ResponseEntity<List<ProductDto>> getProducts() {

    return ResponseEntity.ok(productService.getProducts());
  }

  @PostMapping("/product")
  public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
    ProductDto product = productService.createProduct(productDto);
    return ResponseEntity.created(URI.create("/product/" + product.getId())).body(product);
  }

  @DeleteMapping("/product/{id}")
  public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long id) {
    return ResponseEntity.ok(productService.deleteProduct(id));
  }

  @PutMapping("/product/{id}")
  public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
    return ResponseEntity.ok(productService.updateProduct(id, productDto));
  }

}
