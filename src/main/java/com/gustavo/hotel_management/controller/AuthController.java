package com.gustavo.hotel_management.controller;


import com.gustavo.hotel_management.dto.AuthResponse;
import com.gustavo.hotel_management.dto.LoginRequest;
import com.gustavo.hotel_management.service.AuthService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

 private final AuthService authService;


 public AuthController(AuthService authService) {
  this.authService = authService;
 }

 @PostMapping("/login")
 public AuthResponse login(@RequestBody LoginRequest request){

  return authService.login(request);
 }


}
