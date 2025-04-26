package com.natixis.catalog.backend.controller;

import com.natixis.catalog.backend.dto.UserAuthenticationResponseDto;
import com.natixis.catalog.backend.dto.UserRegistrationResponseDto;
import com.natixis.catalog.backend.dto.UserRequestDto;
import com.natixis.catalog.backend.service.AuthenticationService;
import com.natixis.catalog.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  private final AuthenticationService authenticationService;

  @PostMapping("/register")
  public ResponseEntity<UserRegistrationResponseDto> register(@Valid @RequestBody UserRequestDto userRequest) {
    UserRegistrationResponseDto userResponse = userService.register(userRequest);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new UserRegistrationResponseDto(userResponse.username()));
  }

  @PostMapping("/login")
  public ResponseEntity<UserAuthenticationResponseDto> login(@Valid @RequestBody UserRequestDto user) {
    return ResponseEntity.ok(authenticationService.authenticate(user));
  }
}
