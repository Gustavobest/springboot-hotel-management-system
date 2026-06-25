package com.gustavo.hotel_management.controller;

import com.gustavo.hotel_management.dto.UserRequestDTO;
import com.gustavo.hotel_management.dto.UserResponseDTO;
import com.gustavo.hotel_management.entity.User;
import com.gustavo.hotel_management.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO user)
    {
        UserResponseDTO savedUser = userService.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedUser);

    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> getUsersById(@PathVariable Long id)
    {
        UserResponseDTO user = userService.lookforuser(id);

        return ResponseEntity.ok(user);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public    ResponseEntity<List<UserResponseDTO>> getUsers(){
                List<UserResponseDTO> listuser = userService.findAll();
        return ResponseEntity.ok(listuser);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> updateUsers(@PathVariable Long id , @RequestBody UserRequestDTO user){
         UserResponseDTO  userResponse = userService.update(id , user);
           return ResponseEntity.ok(userResponse);

    }
   @DeleteMapping("/{id}")
   @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);

        return ResponseEntity.noContent().build();

   }
   @GetMapping("/page")
    public  ResponseEntity<Page<UserResponseDTO>> getUsersPaginated
           ( @RequestParam (defaultValue = "0") int page , @RequestParam(defaultValue = "5") int size)
   {
        return ResponseEntity.ok(userService.findAllPaginated(page,size));
   }






}
