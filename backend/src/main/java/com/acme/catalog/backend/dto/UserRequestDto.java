package com.acme.catalog.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserRequestDto {

    @NotBlank(message = "Username is required!")
    @Size(min= 3, message = "Username must have at least 3 characters!")
    @Size(max= 20, message = "Username can have have atmost 20 characters!")
    private String username;

    @NotEmpty(message = "Password is required!")
    @Size(min = 8, message = "Password must be at least 8 characters!")
    private String password;
}