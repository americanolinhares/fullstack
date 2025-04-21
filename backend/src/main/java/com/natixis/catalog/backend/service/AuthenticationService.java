package com.natixis.catalog.backend.service;

import com.natixis.catalog.backend.dto.AuthenticationRequestDto;
import com.natixis.catalog.backend.dto.AuthenticationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password()));
    if (authentication.isAuthenticated()) {
      String token = jwtService.generateToken(request.username());
      return new AuthenticationResponseDto(token);
    }
    return new AuthenticationResponseDto("failed");
  }
}
