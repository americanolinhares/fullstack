package com.natixis.catalog.backend.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class ProductDto {

  private Long id;

  private String name;

  private String description;
}
