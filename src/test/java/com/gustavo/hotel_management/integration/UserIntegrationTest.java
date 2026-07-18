package com.gustavo.hotel_management.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavo.hotel_management.dto.UserRequestDTO;
import com.gustavo.hotel_management.entity.User;
import com.gustavo.hotel_management.repository.UserRepository;
import com.gustavo.hotel_management.security.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@EnableMethodSecurity(prePostEnabled = false)
@Transactional
public class UserIntegrationTest {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private UserRepository userRepository;


    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;



    private UserRequestDTO createUserRequest(){

        UserRequestDTO request = new UserRequestDTO();

        request.setName("User");
        request.setEmail("User@test.com");
        request.setPassword("12345678");
        request.setRole("ADMIN");

        return request;
    }




    // CREATE

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldCreateUserSuccessfully() throws Exception {


        UserRequestDTO request = createUserRequest();


        long countBefore = userRepository.count();



        mockMvc.perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("User"))
                .andExpect(jsonPath("$.email").value("User@test.com"));



        assertEquals(
                countBefore + 1,
                userRepository.count()
        );

    }







    // GET ALL

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void shouldGetAllUsersSuccessfully() throws Exception {


        mockMvc.perform(
                        get("/api/users")
                )
                .andExpect(status().isOk());

    }







    // GET BY ID

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldFindUserByIdSuccessfully() throws Exception {


        User user = new User();

        user.setName("Test User");
        user.setEmail("test@test.com");
        user.setPassword("123456");
        user.setRole("USER");


        User savedUser = userRepository.save(user);



        mockMvc.perform(
                        get("/api/users/" + savedUser.getId())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email")
                        .value("test@test.com"));

    }







    // UPDATE

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldUpdateUserSuccessfully() throws Exception {


        User user = new User();

        user.setName("Old Name");
        user.setEmail("old@test.com");
        user.setPassword("123456");
        user.setRole("USER");


        User savedUser = userRepository.save(user);



        UserRequestDTO request = new UserRequestDTO();

        request.setName("New Name");
        request.setEmail("new@test.com");
        request.setPassword("123456");
        request.setRole("USER");



        mockMvc.perform(
                        put("/api/users/" + savedUser.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value("New Name"));

    }







    // DELETE

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldDeleteUserSuccessfully() throws Exception {


        User user = new User();

        user.setName("Delete User");
        user.setEmail("delete@test.com");
        user.setPassword("123456");
        user.setRole("USER");


        User savedUser = userRepository.save(user);



        long countBefore = userRepository.count();



        mockMvc.perform(
                        delete("/api/users/" + savedUser.getId())
                )
                .andExpect(status().isNoContent());



        assertEquals(
                countBefore - 1,
                userRepository.count()
        );

    }








    // PAGINATION

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void shouldGetUsersPaginatedSuccessfully() throws Exception {


        mockMvc.perform(
                        get("/api/users/page")
                                .param("page","0")
                                .param("size","5")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists());

    }







    // SEARCH

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldSearchUsersSuccessfully() throws Exception {


        mockMvc.perform(
                        get("/api/users/search")
                                .param("name","Gustavo")
                                .param("page","0")
                                .param("size","5")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists());

    }



}