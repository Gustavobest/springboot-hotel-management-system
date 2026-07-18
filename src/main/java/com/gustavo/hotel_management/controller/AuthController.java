package com.gustavo.hotel_management.controller;
import com.gustavo.hotel_management.dto.AuthResponse;
import com.gustavo.hotel_management.dto.LoginRequest;
import com.gustavo.hotel_management.service.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@SecurityRequirement(name = "bearerAuth")

public class AuthController {

 private final AuthService authService;


 public AuthController(AuthService authService)
 {
  this.authService = authService;
 }

 @PostMapping("/login")
 public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
 {
  AuthResponse   requestin  = authService.login(request);
  return ResponseEntity.ok(requestin);

 }


}
