package com.gustavo.hotel_management.service;


import com.gustavo.hotel_management.dto.RoomRequestDTO;
import com.gustavo.hotel_management.dto.RoomResponseDTO;
import com.gustavo.hotel_management.entity.Reservation;
import com.gustavo.hotel_management.entity.Room;
import com.gustavo.hotel_management.exception.RoomHasReservationsException;
import com.gustavo.hotel_management.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {


    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;


    @Test
    void shouldSaveRoomSuccessfully(){


        RoomRequestDTO request = new RoomRequestDTO();
        request.setName("Clasico");
        request.setPrice(1233.0);

        Room roomExists = new Room();
        roomExists.setId(1L);
        roomExists.setName("Clasico");
        roomExists.setPrice(1233.0);

        when(roomRepository.save(any(Room.class))).thenReturn(roomExists);

        RoomResponseDTO response = roomService.save(request);

        assertNotNull(response);
        assertEquals("Clasico",response.getName());
        assertEquals(1233.0,response.getPrice());

        verify(roomRepository).save(any(Room.class));


    }
    @Test
    void shouldListRooms(){
        Room roomExists = new Room();
        roomExists.setId(1L);
        roomExists.setName("Clasico");
        roomExists.setPrice(100.0);



        when(roomRepository.findAll()).thenReturn(List.of(roomExists));

        List<RoomResponseDTO> response = roomService.ListRoom();

        assertEquals(1,response.size());
        assertEquals("Clasico" , response.get(0).getName() );

        verify(roomRepository).findAll();




    }
    @Test
    void shouldEditRoomSuccessfully(){

        Room roomExists = new Room();
        roomExists.setId(1L);
        roomExists.setName("Clasic");
        roomExists.setPrice(100.0);

        RoomRequestDTO request = new RoomRequestDTO();
        request.setName("ultimate");
        request.setPrice(2000.0);

        Room roomUpdate = new Room();
        roomUpdate.setId(1L);
        roomUpdate.setName("ultimate");
        roomUpdate.setPrice(2000.0);


        when(roomRepository.findById(1L)).thenReturn(Optional.of(roomExists));
        when(roomRepository.save(any(Room.class))).thenReturn(roomUpdate);

        RoomResponseDTO response = roomService.editRoom(1L , request);

        verify(roomRepository).findById(1L);
        verify(roomRepository).save(any(Room.class));



    }
    @Test
    void shouldDeleteRoomSuccessfully(){

        Room room = new Room();
        room.setId(1L);
        room.setReservations(List.of());

         when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

         roomService.deleteRoom(1L);


         verify(roomRepository).findById(1L);
         verify(roomRepository).deleteById(1L);

    }
    @Test
    void shouldDeleteRoomFailsHasReservations(){
        Room room = new Room();
        room.setId(1L);
        room.setReservations(List.of(new Reservation()));

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        assertThrows(RoomHasReservationsException.class,
                () -> roomService.deleteRoom(1L));

        verify(roomRepository).findById(1L);
        verify(roomRepository, never()).deleteById(anyLong());
    }
    @Test
    void shouldReturnRoomsPaginated(){

       Room room = new Room();
       room.setId(1L);
       room.setName("gustavo");
       room.setPrice(120.0);

       Page<Room> page = new PageImpl<>(List.of(room));

       when(roomRepository.findAll(any(Pageable.class))).thenReturn(page);

       Page<RoomResponseDTO> response = roomService.findAllPaginated(0,10);

       assertEquals(1,response.getTotalElements());
       assertEquals("gustavo", response.getContent().get(0).getName());

       verify(roomRepository).findAll(any(Pageable.class));

    }
    @Test
    void shouldSearchRoomsSuccessfully(){

        Room room = new Room();
        room.setId(1L);
        room.setName("gustavo");

        Page<Room> page = new PageImpl<>(List.of(room));

        when(roomRepository.findAll(any(Specification.class),any(Pageable.class)))
                .thenReturn(page);
        Page<RoomResponseDTO> response = roomService.searchRooms("gustavo",0,10);

        assertEquals(1,response.getTotalElements());
        assertEquals("gustavo",response.getContent().get(0).getName());

        verify(roomRepository).findAll(any(Specification.class), any(Pageable.class));

    }


















}
