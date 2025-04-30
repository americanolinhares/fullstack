package com.acme.catalog.backend.dto;

import lombok.Builder;

@Builder
public record UserRegistrationResponseDto (String username) {}