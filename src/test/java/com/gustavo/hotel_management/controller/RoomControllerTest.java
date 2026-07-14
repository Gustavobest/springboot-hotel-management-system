package com.gustavo.hotel_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavo.hotel_management.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RoomControllerTest.class)
@AutoConfigureMockMvc(addFilters = false)
public class RoomControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RoomService roomService;

   @Test
    void shouldCreateRoomSuccessFully(){}
    @Test
    void shouldEditRoomSuccessFully(){}
    @Test
    void shouldDeleteRoomSuccessFully(){}
    @Test
    void shouldListAllRoomSuccessFully(){}
    @Test
    void shouldListPaginatedAllRoomSuccessFully(){}
    @Test
    void shouldSearchPaginateRoomSuccessFully(){}










}
