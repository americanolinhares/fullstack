package com.acme.catalog.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductDto {

  private Long id;

  @NotBlank(message = "Product Name is required!")
  @Size(min= 3, message = "Product Name must have at least 3 characters!")
  @Size(max= 20, message = "Product Name can have have atmost 20 characters!")
  private String name;

  @NotBlank(message = "Product Description is required!")
  @Size(min= 3, message = "Product Description must have at least 3 characters!")
  @Size(max= 40, message = "Product Description can have have atmost 20 characters!")
  private String description;
}
