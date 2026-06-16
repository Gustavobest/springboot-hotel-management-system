package com.gustavo.hotel_management.service;

import com.gustavo.hotel_management.dto.AuthResponse;
import com.gustavo.hotel_management.dto.LoginRequest;
import com.gustavo.hotel_management.entity.User;
import com.gustavo.hotel_management.repository.UserRepository;
import com.gustavo.hotel_management.security.JwtService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;


    public AuthService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

  public AuthResponse login (LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        if(!user.getPassword().equals(request.getPassword())){
            throw new RuntimeException("contrasenia incorrecta");
      }
        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);



  }



}
