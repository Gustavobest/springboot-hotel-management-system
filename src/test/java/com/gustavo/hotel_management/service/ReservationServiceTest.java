package com.gustavo.hotel_management.service;


import com.gustavo.hotel_management.dto.ReservationRequestDTO;
import com.gustavo.hotel_management.dto.ReservationResponseDTO;
import com.gustavo.hotel_management.entity.Reservation;
import com.gustavo.hotel_management.entity.Room;
import com.gustavo.hotel_management.entity.User;
import com.gustavo.hotel_management.repository.ReservationRepository;
import com.gustavo.hotel_management.repository.RoomRepository;
import com.gustavo.hotel_management.repository.UserRepository;
import com.gustavo.hotel_management.validation.ReservationValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;
    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ReservationValidator reservationValidator;
    @Mock
    private UserRepository userRepository;


    @Test
    void shouldCreateReservationSuccessfully(){

        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setUserId(1L);
        request.setRoomId(1L);
        request.setCheckInDate(LocalDate.now());
        request.setCheckOutDate(LocalDate.now().plusDays(2));

        User user = new User();
        user.setId(1L);
        user.setName("Gustavo");

        Room room = new Room();
        room.setId(1L);
        room.setName("Suite");

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setRoom(room);
        reservation.setUser(user);
        reservation.setCheckInDate(request.getCheckInDate());
        reservation.setCheckOutDate(request.getCheckOutDate());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

       when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

       ReservationResponseDTO  response = reservationService.create(request);


       assertNotNull(response);
       assertEquals( "Gustavo", response.getUserName());
       assertEquals("Suite",response.getRoomName());
       assertEquals(request.getCheckInDate(),response.getCheckInDate());
       assertEquals(request.getCheckOutDate(),response.getCheckOutDate());

       verify(userRepository).findById(1L);
       verify(roomRepository).findById(1L);
       verify(reservationRepository).save(any(Reservation.class));


    }

    @Test
    void shouldEditReservationSuccessfully(){





        ReservationRequestDTO request = new ReservationRequestDTO();

        request.setRoomId(2L);
        request.setUserId(2L);
        request.setCheckInDate(LocalDate.now());
        request.setCheckOutDate(LocalDate.now().plusDays(2));

        Room room = new Room();
        room.setId(2L);
        room.setName("suite");
        room.setPrice(120.0);

        User user = new User();
        user.setId(2L);
        user.setName("gustavo");



        Reservation reservationExists = new Reservation();
        reservationExists.setUser(user);
        reservationExists.setRoom(room);
        reservationExists.setCheckInDate(request.getCheckInDate());
        reservationExists.setCheckOutDate(LocalDate.now().plusDays(3));


        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(roomRepository.findById(2L)).thenReturn(Optional.of(room));
        when(reservationRepository.findById(2L)).thenReturn(Optional.of(reservationExists));
        when(reservationRepository.save(any(Reservation.class)))
                .thenReturn(reservationExists);

        ReservationResponseDTO response = reservationService.update(2L,request);

        assertNotNull(response);
        assertEquals("gustavo",response.getUserName());
        assertEquals("suite",response.getRoomName());
        assertEquals(request.getCheckInDate(),response.getCheckInDate());
        assertEquals(request.getCheckOutDate(),response.getCheckOutDate());

        verify(reservationRepository).findById(2L);
        verify(userRepository).findById(2L);
        verify(roomRepository).findById(2L);
        verify(reservationRepository).save(any(Reservation.class));


    }

    @Test
    void shouldDeleteReservationSuccessfully(){}
    @Test
    void shouldReturnReservationById(){}
    @Test
    void shouldReturnAllReservation(){}
    @Test
    void shouldReturnReservationsPaginated(){}
    @Test
    void shouldSearchReservationSuccessfully(){}






}
