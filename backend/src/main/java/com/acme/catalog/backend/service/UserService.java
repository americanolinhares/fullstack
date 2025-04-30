package com.acme.catalog.backend.service;

import com.acme.catalog.backend.dto.UserRegistrationResponseDto;
import com.acme.catalog.backend.dto.UserRequestDto;
import com.acme.catalog.backend.entity.User;
import com.acme.catalog.backend.exception.AppException;
import com.acme.catalog.backend.mapper.UserMapper;
import com.acme.catalog.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserRegistrationResponseDto register(UserRequestDto userRequestDto) {

    if (userRepository.existsByUsername(userRequestDto.getUsername())) {
      throw new AppException("User already exists", HttpStatus.BAD_REQUEST);
    }
    userRequestDto.setPassword(bCryptEncoder.encode(userRequestDto.getPassword()));
    User user = userMapper.toUser(userRequestDto);

    return userMapper.toUserRegistrationResponseDto(userRepository.save(user));
  }
}
