package com.gustavo.hotel_management.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavo.hotel_management.dto.ReservationRequestDTO;
import com.gustavo.hotel_management.dto.ReservationResponseDTO;
import com.gustavo.hotel_management.entity.Room;
import com.gustavo.hotel_management.entity.User;
import com.gustavo.hotel_management.repository.ReservationRepository;
import com.gustavo.hotel_management.repository.RoomRepository;
import com.gustavo.hotel_management.repository.UserRepository;
import com.gustavo.hotel_management.security.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.test.context.support.WithMockUser;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@EnableMethodSecurity(prePostEnabled = false)
@Transactional
public class ReservationIntegrationTest {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RoomRepository roomRepository;


    @Autowired
    private ReservationRepository reservationRepository;


    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;



    private User createUser(){

        User user = new User();

        user.setName("User");
        user.setEmail("user@test.com");
        user.setPassword("1234");
        user.setRole("USER");

        return userRepository.save(user);
    }



    private Room createRoom(){

        Room room = new Room();

        room.setName("Suite");
        room.setPrice(150.0);

        return roomRepository.save(room);
    }



    private ReservationRequestDTO createReservationRequest(
            Long userId,
            Long roomId
    ){

        ReservationRequestDTO request = new ReservationRequestDTO();

        request.setUserId(userId);
        request.setRoomId(roomId);
        request.setCheckInDate(LocalDate.now());
        request.setCheckOutDate(LocalDate.now().plusDays(2));

        return request;
    }





    // CREATE

    @Test
    @WithMockUser(username = "test", roles = {"USER"})
    void shouldCreateReservationSuccessfully() throws Exception {


        User user = createUser();

        Room room = createRoom();


        ReservationRequestDTO request =
                createReservationRequest(
                        user.getId(),
                        room.getId()
                );


        long countBefore = reservationRepository.count();



        mockMvc.perform(
                        post("/api/reservations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value("User"))
                .andExpect(jsonPath("$.roomName").value("Suite"));



        assertEquals(
                countBefore + 1,
                reservationRepository.count()
        );

    }






    // READ ALL NORMAL (NO PAGINATION)

    @Test
    @WithMockUser(username = "test", roles = {"USER"})
    void shouldFindAllReservationsSuccessfully() throws Exception {


        mockMvc.perform(
                        get("/api/reservations/all")
                )
                .andExpect(status().isOk());

    }






    // FIND BY ID

    @Test
    @WithMockUser(username = "test", roles = {"ADMIN"})
    void shouldFindReservationByIdSuccessfully() throws Exception {


        User user = createUser();

        Room room = createRoom();


        ReservationRequestDTO request =
                createReservationRequest(
                        user.getId(),
                        room.getId()
                );


        String response = mockMvc.perform(
                        post("/api/reservations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();



        Long reservationId =
                objectMapper
                        .readTree(response)
                        .get("id")
                        .asLong();



        mockMvc.perform(
                        get("/api/reservations/" + reservationId)
                )
                .andExpect(status().isOk());

    }






    // UPDATE

    @Test
    @WithMockUser(username = "test", roles = {"ADMIN"})
    void shouldUpdateReservationSuccessfully() throws Exception {


        User user = new User();
        user.setName("User");
        user.setEmail("user@test.com");
        user.setPassword("1234");
        user.setRole("USER");

        User savedUser = userRepository.save(user);



        Room room = new Room();
        room.setName("Suite");
        room.setPrice(150.0);

        Room savedRoom = roomRepository.save(room);



        ReservationRequestDTO request = new ReservationRequestDTO();

        request.setUserId(savedUser.getId());
        request.setRoomId(savedRoom.getId());
        request.setCheckInDate(LocalDate.now());
        request.setCheckOutDate(LocalDate.now().plusDays(2));



        String response = mockMvc.perform(
                        post("/api/reservations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();



        ReservationResponseDTO created =
                objectMapper.readValue(response, ReservationResponseDTO.class);



        Long reservationId = created.getId();



        request.setCheckOutDate(LocalDate.now().plusDays(5));



        mockMvc.perform(
                        put("/api/reservations/" + reservationId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());

    }






    // DELETE

    @Test
    @WithMockUser(username = "test", roles = {"ADMIN"})
    void shouldDeleteReservationSuccessfully() throws Exception {


        User user = createUser();

        Room room = createRoom();


        ReservationRequestDTO request =
                createReservationRequest(
                        user.getId(),
                        room.getId()
                );


        String response = mockMvc.perform(
                        post("/api/reservations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();



        ReservationResponseDTO created =
                objectMapper.readValue(
                        response,
                        ReservationResponseDTO.class
                );


        Long reservationId = created.getId();



        long countBefore = reservationRepository.count();



        mockMvc.perform(
                        delete("/api/reservations/" + reservationId)
                )
                .andExpect(status().isNoContent());



        assertEquals(
                countBefore - 1,
                reservationRepository.count()
        );


    }}