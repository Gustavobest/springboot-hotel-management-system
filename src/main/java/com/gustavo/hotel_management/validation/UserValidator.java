package com.gustavo.hotel_management.validation;

import com.gustavo.hotel_management.entity.User;
import com.gustavo.hotel_management.exception.UserNotFoundException;
import com.gustavo.hotel_management.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    private final UserRepository userRepository;


    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User validateUserExiste(Long userId)
    {
        return  userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }


}
