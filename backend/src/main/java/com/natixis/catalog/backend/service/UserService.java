package com.natixis.catalog.backend.service;

import com.natixis.catalog.backend.entity.User;
import com.natixis.catalog.backend.exception.AppException;
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

  public User register(User user) {
    if (userRepository.existsByUsername(user.getUsername())) {
      throw new AppException("User already exists", HttpStatus.BAD_REQUEST);
    }

    user.setPassword(bCryptEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }
}
