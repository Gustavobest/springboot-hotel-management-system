package com.gustavo.hotel_management.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavo.hotel_management.dto.RoomRequestDTO;
import com.gustavo.hotel_management.dto.RoomResponseDTO;
import com.gustavo.hotel_management.security.JwtAuthenticationFilter;
import com.gustavo.hotel_management.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(RoomController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RoomControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RoomService roomService;

    @MockitoBean
    private JwtAuthenticationFilter   jwtAuthenticationFilter;


   @Test
    void shouldCreateRoomSuccessFully() throws Exception {
       RoomRequestDTO requestDTO = new RoomRequestDTO();
       requestDTO.setName("Classic");
       requestDTO.setPrice(100.0);

       RoomResponseDTO responseDTO = new RoomResponseDTO();
       responseDTO.setId(1L);
       responseDTO.setName("Classic");
       responseDTO.setPrice(100.00);

       when(roomService.save(any(RoomRequestDTO.class))).thenReturn(responseDTO);
       mockMvc.perform(
               post("/api/rooms").contentType(MediaType.APPLICATION_JSON).content(
                       objectMapper.writeValueAsString(requestDTO)
               )

       )
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.name").value("Classic"))
               .andExpect(jsonPath("$.price").value(100.0));

          verify(roomService).save(any(RoomRequestDTO.class));


   }
    @Test
    void shouldEditRoomSuccessFully() throws Exception {

       RoomResponseDTO responseDTO = new RoomResponseDTO();
       responseDTO.setId(1L);
       responseDTO.setName("Classic");
       responseDTO.setPrice(100.0);

       RoomRequestDTO requestDTO = new RoomRequestDTO();
       requestDTO.setName("Classic");
       requestDTO.setPrice(100.0);


       when(roomService.editRoom(eq(1L), any(RoomRequestDTO.class))).thenReturn(responseDTO);

       mockMvc.perform(
               put("/api/rooms/1").contentType(MediaType.APPLICATION_JSON).content(
                       objectMapper.writeValueAsString(requestDTO)
               )

       )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.name").value("Classic"))
               .andExpect(jsonPath("$.price").value(100.0));

       verify(roomService).editRoom(eq(1L),any(RoomRequestDTO.class));
    }
    @Test
    void shouldDeleteRoomSuccessFully() throws Exception{


       doNothing().when(roomService).deleteRoom(1L);

       mockMvc.perform(
               delete("/api/rooms/1"))
               .andExpect(status().isNoContent());

       verify(roomService).deleteRoom(1L);

    }
    @Test
    void shouldListAllRoomSuccessFully() throws Exception {
       RoomResponseDTO responseDTO = new RoomResponseDTO();
       responseDTO.setId(1L);
       responseDTO.setName("Classic");
       responseDTO.setPrice(100.0);

        List<RoomResponseDTO> lista = new ArrayList<>();
        lista.add(responseDTO);

        when(roomService.ListRoom()).thenReturn(lista);

        mockMvc.perform(
                get("/api/rooms").contentType(MediaType.APPLICATION_JSON)

        )
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Classic"))
                .andExpect(jsonPath("$[0].price").value(100.0));

         verify(roomService).ListRoom();

    }
    @Test
    void shouldListPaginatedAllRoomSuccessFully() throws Exception {
        RoomResponseDTO responseDTO = new RoomResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Classic");
        responseDTO.setPrice(100.0);


        Page<RoomResponseDTO> page = new PageImpl<>(List.of(responseDTO));

        when(roomService.findAllPaginated(0,10)).thenReturn(page);

        mockMvc.perform(
                get("/api/rooms/page")
                        .param("page" , "0")
                        .param("size" , "10")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Classic"))
                .andExpect(jsonPath("$.content[0].price").value(100.0));

        verify(roomService).findAllPaginated(0,10);

    }
    @Test
    void shouldSearchPaginateRoomSuccessFully() throws Exception{
       RoomResponseDTO responseDTO = new RoomResponseDTO();
       responseDTO.setId(1L);
       responseDTO.setName("Classic");
       responseDTO.setPrice(100.0);

       Page<RoomResponseDTO> page = new PageImpl<>(List.of(responseDTO));
       when(roomService.searchRooms(("Classic"), 0 ,10 )).thenReturn(page);

       mockMvc.perform(
               get("/api/rooms/search")
                       .param("name" , "Classic")
                       .param("page" , "0")
                       .param("size" , "10")
       )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content",hasSize(1)))
               .andExpect(jsonPath("$.content[0].name").value("Classic"));

        verify(roomService).searchRooms(("Classic"), 0 ,10);


    }










}
