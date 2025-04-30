package com.acme.catalog.backend.service;

import com.acme.catalog.backend.dto.UserAuthenticationResponseDto;
import lombok.RequiredArgsConstructor;
import com.acme.catalog.backend.dto.UserRequestDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public UserAuthenticationResponseDto authenticate(UserRequestDto request) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    if (authentication.isAuthenticated()) {
      String token = jwtService.generateToken(request.getUsername());
      return new UserAuthenticationResponseDto(token);
    }

    return new UserAuthenticationResponseDto("Failed");
  }
}
