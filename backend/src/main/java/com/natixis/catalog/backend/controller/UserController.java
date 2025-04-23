package com.natixis.catalog.backend.controller;

import com.natixis.catalog.backend.dto.AuthenticationRequestDto;
import com.natixis.catalog.backend.dto.AuthenticationResponseDto;
import com.natixis.catalog.backend.entity.User;
import com.natixis.catalog.backend.service.AuthenticationService;
import com.natixis.catalog.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
  public ResponseEntity<User> register(@Valid @RequestBody User user) {
    return ResponseEntity.ok(userService.register(user));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
    return ResponseEntity.ok(authenticationService.authenticate(authenticationRequestDto));
  }
}
