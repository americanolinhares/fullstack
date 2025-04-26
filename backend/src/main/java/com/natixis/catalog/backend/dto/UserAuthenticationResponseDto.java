package com.natixis.catalog.backend.dto;
import lombok.Builder;

@Builder
public record UserAuthenticationResponseDto(String token) {}
