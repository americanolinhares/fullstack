package com.acme.catalog.backend.controller;

import com.acme.catalog.backend.dto.UserAuthenticationResponseDto;
import com.acme.catalog.backend.dto.UserRegistrationResponseDto;
import com.acme.catalog.backend.dto.UserRequestDto;
import com.acme.catalog.backend.service.AuthenticationService;
import com.acme.catalog.backend.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "users", description = "Operations about users")
public class UserController {

  private final UserService userService;

  private final AuthenticationService authenticationService;

  @PostMapping("/register")
  @Operation(summary = "Create an user", tags = {"users"})
  public ResponseEntity<UserRegistrationResponseDto> register(@Valid @RequestBody UserRequestDto userRequest) {
    UserRegistrationResponseDto userResponse = userService.register(userRequest);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new UserRegistrationResponseDto(userResponse.username()));
  }

  @PostMapping("/login")
  @Operation(summary = "Generate the auth token of an existing user", tags = {"users"})
  public ResponseEntity<UserAuthenticationResponseDto> login(@Valid @RequestBody UserRequestDto user) {
    return ResponseEntity.ok(authenticationService.authenticate(user));
  }
}
