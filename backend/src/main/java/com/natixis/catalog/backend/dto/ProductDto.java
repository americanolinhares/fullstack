package com.natixis.catalog.backend.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductDto {

  private Long id;

  private String name;

  private String description;
}
