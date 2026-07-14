package com.gustavo.hotel_management.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavo.hotel_management.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ReservationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReservationControllerTest {

    @Autowired
    private MockMvc  mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ReservationService reservationService;

    @Test
    void shouldCreateReservationSuccessFully(){}
    @Test
    void shouldDeleteReservationSuccessFully(){}
    @Test
    void shouldEditReservationSuccessFully(){}
    @Test
    void shouldListAllReservationSuccessFully(){}
    @Test
    void shouldListPaginateAllReservationSuccessFully(){}
    @Test
    void shouldSearchPaginateReservationSuccessFully(){}
    @Test
    void shouldSearchReservationForIdSuccessFully(){}

}
