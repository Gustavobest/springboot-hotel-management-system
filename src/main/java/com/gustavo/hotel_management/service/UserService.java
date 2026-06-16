package com.gustavo.hotel_management.service;

import com.gustavo.hotel_management.entity.User;
import com.gustavo.hotel_management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public  User lookforuser(Long id) {

        User userExist = userRepository.findById(id)
                .orElseThrow();




        return userExist;

    }

    public User save(User user){
        return userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User update(Long id, User user) {

        User existUser =  userRepository.findById(id)
                .orElseThrow();

        existUser.setName(user.getName());
        existUser.setEmail(user.getEmail());
        existUser.setPassword(user.getPassword());
        existUser.setRole(user.getRole());
        return userRepository.save(existUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}