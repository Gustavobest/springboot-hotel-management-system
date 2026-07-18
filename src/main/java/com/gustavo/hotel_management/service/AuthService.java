package com.gustavo.hotel_management.service;
import com.gustavo.hotel_management.dto.AuthResponse;
import com.gustavo.hotel_management.dto.LoginRequest;
import com.gustavo.hotel_management.entity.User;
import com.gustavo.hotel_management.repository.UserRepository;
import com.gustavo.hotel_management.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public AuthResponse login (LoginRequest request){
        log.info("login attempt initiated for email {} ", request.getEmail());
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->{
                    log.warn("Login faild: Email {} not found" , request.getEmail());
                    return new RuntimeException("User noy found");
                });


        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            log.warn("Login failed : invalid password for email {}", request.getEmail());
            throw new RuntimeException("Incorrect password");
      }
        String token = jwtService.generateToken(user.getEmail());

        log.info("Login successful. Token generated for user: {}" , user.getEmail());

        return new AuthResponse(token);



  }



}
