package com.gustavo.hotel_management.service;
import com.gustavo.hotel_management.dto.UserRequestDTO;
import com.gustavo.hotel_management.dto.UserResponseDTO;
import com.gustavo.hotel_management.entity.User;
import com.gustavo.hotel_management.exception.UserNotFoundException;
import com.gustavo.hotel_management.mapper.UserMapper;
import com.gustavo.hotel_management.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.gustavo.hotel_management.specification.UserSpecification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
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

        return  UserMapper.toDTO(userExist);

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
        log.info("Creating user with email:{}", userDto.getEmail());
        User savedUser = userRepository.save(user);
        log.info("User seccessfully created with ID: {}", savedUser.getId());

        return UserMapper.toDTO(savedUser);
    }
    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll(){

        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO).toList();

    }
    @Transactional
    public UserResponseDTO update(Long id, UserRequestDTO userDto) {

        User existUser =  userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException(id));

        existUser.setName(userDto.getName());
        existUser.setEmail(userDto.getEmail());
        existUser.setRole(userDto.getRole());

        User savedUser = userRepository.save(existUser);
        return  UserMapper.toDTO(savedUser);
    }
    @Transactional
    public void deleteUser(Long id) {

       userRepository.findById(id)
                        .orElseThrow(() ->  new UserNotFoundException(id));
            log.info("Deleting user {}",id);
        userRepository.deleteById(id);
        log.info("User {} deleted successfully" , id);
    }
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> findAllPaginated(int page  , int size )
    {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable)
                .map(UserMapper::toDTO);



    }
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> searchUsers(String name, int page, int size)
    {
        Pageable pageable = PageRequest.of(page,size);
        log.debug("Searching user {}" , name );
        Specification<User> spec = Specification.where(UserSpecification.hasName(name));
        Page<User> users = userRepository.findAll(spec,pageable);
        return users.map(UserMapper::toDTO);
    }





}
























