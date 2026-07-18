package com.gustavo.hotel_management.integration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavo.hotel_management.dto.RoomRequestDTO;
import com.gustavo.hotel_management.entity.Room;
import com.gustavo.hotel_management.repository.RoomRepository;
import com.gustavo.hotel_management.security.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@EnableMethodSecurity(prePostEnabled = false)
@Transactional
public class RoomIntegrationTest {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private RoomRepository roomRepository;


    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;



    private RoomRequestDTO createRoomRequest(){

        RoomRequestDTO request = new RoomRequestDTO();

        request.setName("Suite");
        request.setPrice(150.0);

        return request;
    }



    // CREATE

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldCreateRoomSuccessfully() throws Exception {


        RoomRequestDTO request = createRoomRequest();


        long countBefore = roomRepository.count();



        mockMvc.perform(
                        post("/api/rooms")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Suite"))
                .andExpect(jsonPath("$.price").value(150.0));



        assertEquals(
                countBefore + 1,
                roomRepository.count()
        );

    }





    // LIST ALL

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void shouldListRoomsSuccessfully() throws Exception {


        mockMvc.perform(
                        get("/api/rooms")
                )
                .andExpect(status().isOk());

    }





    // UPDATE

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldUpdateRoomSuccessfully() throws Exception {


        Room room = new Room();

        room.setName("Simple");
        room.setPrice(100.0);


        Room savedRoom = roomRepository.save(room);



        RoomRequestDTO request = new RoomRequestDTO();

        request.setName("Suite Deluxe");
        request.setPrice(250.0);



        mockMvc.perform(
                        put("/api/rooms/" + savedRoom.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Suite Deluxe"));

    }





    // DELETE

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldDeleteRoomSuccessfully() throws Exception {


        Room room = new Room();

        room.setName("Suite");

        room.setPrice(150.0);


        Room savedRoom = roomRepository.save(room);



        long countBefore = roomRepository.count();



        mockMvc.perform(
                        delete("/api/rooms/" + savedRoom.getId())
                )
                .andExpect(status().isNoContent());



        assertEquals(
                countBefore - 1,
                roomRepository.count()
        );

    }





    // PAGINATION

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void shouldGetRoomsPaginatedSuccessfully() throws Exception {


        mockMvc.perform(
                        get("/api/rooms/page")
                                .param("page","0")
                                .param("size","5")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists());

    }





    // SEARCH

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void shouldSearchRoomsSuccessfully() throws Exception {


        mockMvc.perform(
                        get("/api/rooms/search")
                                .param("name","Suite")
                                .param("page","0")
                                .param("size","5")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists());

    }


}