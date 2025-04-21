package com.natixis.catalog.backend.controller;

import com.natixis.catalog.backend.entity.User;
import com.natixis.catalog.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired private UserService userService;

  @PostMapping("/register")
  public ResponseEntity<User> register(@Valid @RequestBody User user) {

    return ResponseEntity.ok(userService.register(user));
  }
}
