package com.gustavo.hotel_management.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavo.hotel_management.dto.ReservationRequestDTO;
import com.gustavo.hotel_management.dto.ReservationResponseDTO;
import com.gustavo.hotel_management.security.JwtAuthenticationFilter;
import com.gustavo.hotel_management.service.ReservationService;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.UIManager.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReservationControllerTest {

    @Autowired
    private MockMvc  mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ReservationService reservationService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void shouldCreateReservationSuccessFully()  throws  Exception{

        ReservationResponseDTO responseDTO = new ReservationResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setUserName("User");
        responseDTO.setRoomName("Classic");
        responseDTO.setCheckInDate(LocalDate.now());
        responseDTO.setCheckOutDate(LocalDate.now().plusDays(1));




        ReservationRequestDTO requestDTO = new ReservationRequestDTO();
        requestDTO.setUserId(1L);
        requestDTO.setRoomId(1L);
        requestDTO.setCheckInDate(LocalDate.now());
        requestDTO.setCheckOutDate(LocalDate.now().plusDays(1));


        when(reservationService.create(any(ReservationRequestDTO.class))).thenReturn(responseDTO);
        mockMvc.perform(
                post("/api/reservations").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userName").value("User"))
                .andExpect(jsonPath("$.roomName").value("Classic"))
                .andExpect(jsonPath("$.checkInDate").exists())
                .andExpect(jsonPath("$.checkOutDate").exists());

                verify(reservationService).create(any(ReservationRequestDTO.class));


    }
    @Test
    void shouldDeleteReservationSuccessFully() throws  Exception{

        doNothing().when(reservationService).delete(1L);

        mockMvc.perform(
                delete("/api/reservations/1"))
                .andExpect(status().isNoContent());

        verify(reservationService).delete(1L);



    }
    @Test
    void shouldEditReservationSuccessFully() throws  Exception{
        ReservationResponseDTO responseDTO = new ReservationResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setUserName("User");
        responseDTO.setRoomName("Classic");
        responseDTO.setCheckInDate(LocalDate.now());
        responseDTO.setCheckOutDate(LocalDate.now().plusDays(2));

        ReservationRequestDTO requestDTO = new ReservationRequestDTO();
        requestDTO.setRoomId(1L);
        requestDTO.setUserId(1L);
        requestDTO.setCheckInDate(LocalDate.now());
        requestDTO.setCheckOutDate(LocalDate.now().plusDays(2));

        when(reservationService.update(eq(1L) , any(ReservationRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(
                put("/api/reservations/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
        )
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.roomName").value("Classic"))
                .andExpect(jsonPath("$.userName").value("User"))
                .andExpect(jsonPath("$.checkInDate").exists())
                .andExpect(jsonPath("$.checkOutDate").exists());

        verify(reservationService).update(eq(1L), any(ReservationRequestDTO.class));





    }
    @Test
    void shouldListAllReservationSuccessFully() throws Exception{

        ReservationResponseDTO responseDTO = new ReservationResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setUserName("User");
        responseDTO.setRoomName("Classic");
        responseDTO.setCheckInDate(LocalDate.now());
        responseDTO.setCheckOutDate(LocalDate.now().plusDays(2));

        List<ReservationResponseDTO> lista = new ArrayList<>();
        lista.add(responseDTO);

        when(reservationService.ListAll()).thenReturn(lista);

        mockMvc.perform(
                get("/api/reservations/all").contentType(MediaType.APPLICATION_JSON)

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].userName").value("User"))
                .andExpect(jsonPath("$[0].roomName").value("Classic"))
                .andExpect(jsonPath("$[0].checkInDate").exists())
                .andExpect(jsonPath("$[0].checkOutDate").exists());

        verify(reservationService).ListAll();


    }
    @Test
    void shouldListPaginateAllReservationSuccessFully() throws  Exception{

        ReservationResponseDTO responseDTO = new ReservationResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setUserName("User");
        responseDTO.setRoomName("Classic");
        responseDTO.setCheckInDate(LocalDate.now());
        responseDTO.setCheckOutDate(LocalDate.now().plusDays(2));

        Page<ReservationResponseDTO> page = new PageImpl<>(List.of(responseDTO));

        when(reservationService.findAllPaginated(0,10)).thenReturn(page);

        mockMvc.perform(
                        get("/api/reservations")
                                .param("page" , "0")
                                .param("size" , "10")

                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].userName").value("User"))
                .andExpect(jsonPath("$.content[0].roomName").value("Classic"))
                .andExpect(jsonPath("$.content[0].checkInDate").exists())
                .andExpect(jsonPath("$.content[0].checkOutDate").exists());

        verify(reservationService).findAllPaginated(0,10);


    }
    @Test
    void shouldSearchPaginateReservationSuccessFully() throws  Exception{

        ReservationResponseDTO responseDTO = new ReservationResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setRoomName("Classic");
        responseDTO.setUserName("User");
        responseDTO.setCheckInDate(LocalDate.now());
        responseDTO.setCheckOutDate(LocalDate.now().plusDays(2));

        Page<ReservationResponseDTO> pageable = new PageImpl<>(List.of(responseDTO));

        when(reservationService.findAllPaginated(0,10)).thenReturn(pageable);

        mockMvc.perform(
                get("/api/reservations")
                        .param("page","0")
                        .param("size","10")
        )
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].roomName").value("Classic"))
                .andExpect(jsonPath("$.content[0].userName").value("User"))
                .andExpect(jsonPath("$.content[0].checkInDate").exists())
                .andExpect(jsonPath("$.content[0].checkOutDate").exists());

        verify(reservationService).findAllPaginated(0,10);



    }
    @Test
    void shouldSearchReservationForIdSuccessFully() throws  Exception{

        ReservationResponseDTO responseDTO = new ReservationResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setRoomName("Classic");
        responseDTO.setUserName("User");
        responseDTO.setCheckInDate(LocalDate.now());
        responseDTO.setCheckOutDate(LocalDate.now().plusDays(2));

        Page<ReservationResponseDTO> pageable = new PageImpl<>(List.of(responseDTO));

        when(reservationService.findAllPaginated(0,10)).thenReturn(pageable);

        mockMvc.perform(
                        get("/api/reservations")
                                .param("userName","User")
                                .param("roomName","Classic")
                                .param("page","0")
                                .param("size","10")
                )
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].roomName").value("Classic"))
                .andExpect(jsonPath("$.content[0].userName").value("User"))
                .andExpect(jsonPath("$.content[0].checkInDate").exists())
                .andExpect(jsonPath("$.content[0].checkOutDate").exists());

        verify(reservationService).findAllPaginated(0,10);


    }

}
