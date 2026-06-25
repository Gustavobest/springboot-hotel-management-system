package com.gustavo.hotel_management.service;

import com.gustavo.hotel_management.dto.UserRequestDTO;
import com.gustavo.hotel_management.dto.UserResponseDTO;
import com.gustavo.hotel_management.entity.User;
import com.gustavo.hotel_management.exception.UserNotFoundException;
import com.gustavo.hotel_management.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.gustavo.hotel_management.specification.UserSpecification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    @Transactional(readOnly = true)
    public UserResponseDTO lookforuser(Long id) {

        User userExist = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return new UserResponseDTO(
                userExist.getId(),
                userExist.getName(),
                userExist.getEmail(),
                userExist.getRole());

    }
    @Transactional
    public UserResponseDTO save(UserRequestDTO userDto){

        User user = new User();

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(
                passwordEncoder.encode(userDto.getPassword())
        );
        user.setRole(userDto.getRole());

        User savedUser = userRepository.save(user);


        return new UserResponseDTO(
          savedUser.getId(),
          savedUser.getName(),
          savedUser.getEmail(),
          savedUser.getRole()
        );
    }
    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll(){

        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole()

                )).toList();

    }
    @Transactional
    public UserResponseDTO update(Long id, UserRequestDTO userDto) {

        User existUser =  userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException(id));

        existUser.setName(userDto.getName());
        existUser.setEmail(userDto.getEmail());
        existUser.setRole(userDto.getRole());

        User savedUser = userRepository.save(existUser);
        return  new UserResponseDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }
    @Transactional
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                        .orElseThrow(() ->  new UserNotFoundException(id));

        userRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> findAllPaginated(int page  , int size )
    {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable)
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole()

                ));



    }




}