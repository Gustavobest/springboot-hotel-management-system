package com.gustavo.hotel_management.service;

import com.gustavo.hotel_management.dto.UserRequestDTO;
import com.gustavo.hotel_management.dto.UserResponseDTO;
import com.gustavo.hotel_management.entity.User;
import com.gustavo.hotel_management.repository.UserRepository;
import com.gustavo.hotel_management.specification.UserSpecification;
import jakarta.validation.constraints.NotNull;
import org.assertj.core.api.DateAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldSaveUserSuccessfully() {

        // 1. Arrange (preparar datos)
        UserRequestDTO request = new UserRequestDTO();
        request.setName("Diego");
        request.setEmail("Diego@test.com");
        request.setPassword("1234");
        request.setRole("USER");

        when(passwordEncoder.encode("1234")).thenReturn("encrypted1234");

        User savedUser = new User();
        savedUser.setId(0L);
        savedUser.setName("Diego");
        savedUser.setEmail("Diego@test.com");
        savedUser.setPassword("encrypted1234");
        savedUser.setRole("USER");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // 2. Act (ejecutar método)
        UserResponseDTO response = userService.save(request);

        // 3. Assert (verificar resultados)
        assertNotNull(response);
        assertEquals("Diego", response.getName());
        assertEquals("Diego@test.com", response.getEmail());
        assertEquals("USER",response.getRole());

        // 4. Verify (verificar interacciones)
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode("1234");
    }
    @Test
    void shouldReturnAllUsers() {
     //Arranque
        User user = new User();
        user.setId((1L));
        user.setName("Diego");
        user.setEmail("diego@test.com");
        user.setRole("USER");

        List<User> users = List.of(user);

        when(userRepository.findAll()).thenReturn(users);

        List<UserResponseDTO> response = userService.findAll();

        assertNotNull (response);
        assertEquals(1, response.size());
        assertEquals("Diego" , response.get(0).getName());
        assertEquals("diego@test.com", response.get(0).getEmail());
        assertEquals("USER", response.get(0).getRole());

        verify(userRepository, times(1)).findAll();


    }
    @Test
    void shouldUpdateUsersSuccessfully(){

        User existingUser = new User();
        existingUser.setId((2L));
        existingUser.setName("Wilson");
        existingUser.setEmail("Wilson@test.com");
        existingUser.setRole("ADMIN");

        UserRequestDTO request = new UserRequestDTO();
        request.setName("Carlos");
        request.setEmail("Carlos@test.com");
        request.setRole("USER");

        User  updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setName("Carlos");
        updatedUser.setEmail("Carlos@test.com");
        updatedUser.setRole("USER");


        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class)))
                .thenReturn(updatedUser);

        UserResponseDTO response = userService.update(1L,request);

        assertNotNull(response);
        assertEquals("Carlos" , response.getName());
        assertEquals("Carlos@test.com" , response.getEmail());
        assertEquals("USER",response.getRole());


        verify(userRepository).findById(1L);
        verify(userRepository).save(any(User.class));



    }
    @Test
    void shouldDeleteUserSuccessfully(){

        User userExists = new User();
        userExists.setId(2L);
        userExists.setName("Gustavo");
        userExists.setEmail("Gustavo@test.com");
        userExists.setPassword("Gustavo123");
        userExists.setRole("ADMIN");

        when(userRepository.findById(2L)).thenReturn(Optional.of(userExists));

        userService.deleteUser(2L);
        verify(userRepository).findById(2L);
        verify(userRepository).deleteById(2L);



    }
    @Test
    void shouldSearchUsersSuccessfully(){

        User userExists = new User();
        userExists.setId(1L);
        userExists.setName("Vianca");
        userExists.setEmail("vianca@test.com");
        userExists.setPassword("vianca123");
        userExists.setRole("USER");

        Page<User> page = new PageImpl<>(List.of(userExists));

        when(userRepository.findAll(any(Specification.class),any(Pageable.class))).thenReturn(page);

        Page<UserResponseDTO> response = userService.searchUsers("Vianca",0,10);

        assertNotNull(response);
        assertEquals(1,response.getTotalElements());
        assertEquals("Vianca", response.getContent().get(0).getName());

        verify(userRepository).findAll(
                any(Specification.class),
                any(Pageable.class)
        );


    }
    @Test
    void shouldReturnUsersPaginated(){

        User userExists = new User();
        userExists.setId(1L);
        userExists.setName("Luis");
        userExists.setEmail("Luis@test.com");
        userExists.setPassword("123");
        userExists.setRole("ADMIN");

        Page<User> page = new PageImpl<>(List.of(userExists));

        when(userRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<UserResponseDTO> response = userService.findAllPaginated(0,10);

       assertNotNull(response);
       assertEquals(1,response.getTotalElements());
       assertEquals("Luis", response.getContent().get(0).getName());

       verify(userRepository).findAll(
               any(Pageable.class)
       );


    }
    @Test
    void shouldReturnUserById(){

        User user = new User();
        user.setId(1L);
        user.setName("Diego");
        user.setEmail("diego@test.com");
        user.setRole("USER");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDTO response = userService.lookforuser(1L);

        assertNotNull(response);
        assertEquals("Diego",response.getName());
        assertEquals("diego@test.com",response.getEmail());

        verify(userRepository).findById(1L);
    }





}