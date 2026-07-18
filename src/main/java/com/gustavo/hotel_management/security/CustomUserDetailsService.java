package com.gustavo.hotel_management.security;

import com.gustavo.hotel_management.entity.User;
import com.gustavo.hotel_management.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService {


    private final UserRepository userRepository;


    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       User user = userRepository.findByEmail(email)
               .orElseThrow(() ->  new UsernameNotFoundException ("User not found"));

       return org.springframework.security.core.userdetails.User
               .withUsername(user.getEmail())
               .password(user.getPassword())
               .roles(user.getRole())
               .build();
    }



}
