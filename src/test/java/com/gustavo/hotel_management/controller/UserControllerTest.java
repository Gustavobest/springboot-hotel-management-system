package com.gustavo.hotel_management.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavo.hotel_management.dto.UserRequestDTO;
import com.gustavo.hotel_management.dto.UserResponseDTO;
import com.gustavo.hotel_management.security.JwtAuthenticationFilter;
import com.gustavo.hotel_management.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(
        value = UserController.class
            ,excludeAutoConfiguration = {
        SecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class
}
)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtAuthenticationFilter  jwtAuthenticationFilter;

   @Test
    void shouldCreateUserSuccessfully() throws Exception{

       UserRequestDTO request = new UserRequestDTO();
       request.setName("Gustavo");
       request.setEmail("gustavo@test.com");
       request.setPassword("gustavo123");
       request.setRole("USER");


       UserResponseDTO response = new UserResponseDTO();
       response.setId(1L);
       response.setName("Gustavo");
       response.setEmail("gustavo@test.com");
       response.setRole("USER");

       when(userService.save(any(UserRequestDTO.class))).thenReturn(response);

       mockMvc.perform(
               post("/api/users").contentType(MediaType.APPLICATION_JSON).content(
                       objectMapper.writeValueAsString(request)
               )

       )
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.name").value("Gustavo"))
               .andExpect(jsonPath("$.email").value("gustavo@test.com"))
               .andExpect(jsonPath("$.role").value("USER"));

       verify(userService).save(any(UserRequestDTO.class));



   }


   @Test
    void shouldEditUserSuccessfully() throws Exception {

       UserRequestDTO requestDTO = new UserRequestDTO();
       requestDTO.setName("Diego");
       requestDTO.setEmail("diego@test.com");
       requestDTO.setPassword("diego123");
       requestDTO.setRole("ADMIN");


       UserResponseDTO  responseDTO = new UserResponseDTO();
       responseDTO.setId(1L);
       responseDTO.setName("Diego");
       responseDTO.setRole("ADMIN");
       responseDTO.setEmail("diego@test.com");


       when(userService.update(eq(1L), any(UserRequestDTO.class))).thenReturn(responseDTO);

       mockMvc.perform(
               put("/api/users/1")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(requestDTO))

       ).andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("Diego"))
               .andExpect(jsonPath("$.email").value("diego@test.com"))
               .andExpect(jsonPath("$.role").value("ADMIN"));

       verify(userService).update(eq(1L), any(UserRequestDTO.class));
   }

    @Test
    void shouldReturnUserById() throws Exception {

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("user");
        responseDTO.setEmail("user@test.com");
        responseDTO.setRole("ADMIN");

        when(userService.lookforuser(1L))
                .thenReturn(responseDTO);

        mockMvc.perform(
                        get("/api/users/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("user"))
                .andExpect(jsonPath("$.email").value("user@test.com"))
                .andExpect(jsonPath("$.role").value("ADMIN"));

        verify(userService).lookforuser(1L);
    }
    @Test
    void shouldReturnAllUsers() throws Exception{

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("user");
        responseDTO.setEmail("user@test.com");
        responseDTO.setRole("ADMIN");

        List<UserResponseDTO> lista = new ArrayList<>();
        lista.add(responseDTO);

        when(userService.findAll()).thenReturn(lista);

        mockMvc.perform(
                get("/api/users")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("user"))
                .andExpect(jsonPath("$[0].email").value("user@test.com"))
                .andExpect(jsonPath("$[0].role").value("ADMIN"));

        verify(userService).findAll();



    }
    @Test
    void shouldDeleteUserSuccessfully()  throws Exception {



       when(userService.lookforuser(1L)).thenReturn(new UserResponseDTO());

       doNothing().when(userService).deleteUser(1L);

       mockMvc.perform(
               delete("/api/users/1")
               )
                       .andExpect(status().isNoContent());


       verify(userService).deleteUser(1L);

    }
    @Test
    void shouldReturnUsersPaginated()  throws Exception{

       UserResponseDTO responseDTO = new UserResponseDTO();
       responseDTO.setId(1L);
       responseDTO.setRole("ADMIN");
       responseDTO.setName("user");
       responseDTO.setEmail("user@test.com");

       List<UserResponseDTO> lista = List.of(responseDTO);
       Page<UserResponseDTO> pageable = new PageImpl<>(lista);

       when(userService.findAllPaginated(0,10)).thenReturn(pageable);


       mockMvc.perform(
               get("/api/users/page")
                       .param("page" ,"0")
                       .param("size","10")
       )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content[0].id").value(1))
               .andExpect(jsonPath("$.content[0].name").value("user"))
               .andExpect(jsonPath("$.content[0].email").value("user@test.com"));

       verify(userService).findAllPaginated(0,10);



    }
    @Test
    void shouldSearchUsersSuccessfully() throws Exception{

       UserResponseDTO responseDTO = new UserResponseDTO();
       responseDTO.setId(1L);
       responseDTO.setName("user");
       responseDTO.setEmail("user@test.com");
       responseDTO.setRole("ADMIN");

           List<UserResponseDTO> dtoList = List.of(responseDTO);

          Page<UserResponseDTO> userPage = new PageImpl<>(dtoList , PageRequest.of(0,10),dtoList.size());

       when(userService.searchUsers("user",0,10)).thenReturn(userPage);


       mockMvc.perform(
               get("/api/users/search")
                       .param("name","user")
                       .param("page","0")
                       .param("size" ,"10")
       )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content[0].id").value(1))
               .andExpect(jsonPath("$.content[0].name").value("user"))
               .andExpect(jsonPath("$.content[0].email").value("user@test.com"))
               .andExpect(jsonPath("$.content[0].role").value("ADMIN"));


       verify(userService).searchUsers("user",0,10);



    }



}
