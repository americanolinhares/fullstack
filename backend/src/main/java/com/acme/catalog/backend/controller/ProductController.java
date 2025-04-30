package com.acme.catalog.backend.controller;

import com.acme.catalog.backend.dto.ProductDto;
import com.acme.catalog.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
@Tag(name = "products", description = "Operations about products")
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = "Bearer Authentication")
public class ProductController {

  private final ProductService productService;

  @GetMapping
  @Operation(summary = "List all Products", tags = {"products"})
  public ResponseEntity<List<ProductDto>> getAllProducts() {
    return ResponseEntity.ok(productService.getAllProducts());
  }

  @GetMapping("/{id}")
  @Operation(summary = "List a specific Product", tags = {"products"})
  public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
    return ResponseEntity.ok(productService.getProductById(id));
  }

  @PostMapping
  @Operation(summary = "Create a Product", tags = {"products"})
  public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
    ProductDto product = productService.createProduct(productDto);
    return ResponseEntity.created(URI.create("/product/" + product.getId())).body(product);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a Product", tags = {"products"})
  public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long id) {
    return ResponseEntity.ok(productService.deleteProduct(id));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update a Product", tags = {"products"})
  public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
    return ResponseEntity.ok(productService.updateProduct(id, productDto));
  }
}
