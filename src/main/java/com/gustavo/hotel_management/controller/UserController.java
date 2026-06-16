package com.gustavo.hotel_management.controller;

import com.gustavo.hotel_management.entity.User;
import com.gustavo.hotel_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user){
        return userService.save(user);
    }
    @GetMapping("/{id}")
    public User lookforuser(@PathVariable Long id)
    {
        return userService.lookforuser(id);
    }



    @GetMapping
    public List<User> getUsers(){
        return userService.findAll();
    }
    @PutMapping("/{id}")
    public User updateUsers(@PathVariable Long id , @RequestBody User user){
        return userService.update(id , user);
    }
   @DeleteMapping("/{id}")
    public  void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
   }






}
