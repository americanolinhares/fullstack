package com.natixis.catalog.backend.service;

import com.natixis.catalog.backend.dto.UserRegistrationResponseDto;
import com.natixis.catalog.backend.dto.UserRequestDto;
import com.natixis.catalog.backend.entity.User;
import com.natixis.catalog.backend.exception.AppException;
import com.natixis.catalog.backend.mapper.UserMapper;
import com.natixis.catalog.backend.repository.UserRepository;
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
