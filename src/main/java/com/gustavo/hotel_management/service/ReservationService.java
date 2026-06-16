package com.gustavo.hotel_management.service;

import com.gustavo.hotel_management.entity.Reservation;
import com.gustavo.hotel_management.entity.Room;
import com.gustavo.hotel_management.entity.User;
import com.gustavo.hotel_management.repository.ReservationRepository;
import com.gustavo.hotel_management.repository.RoomRepository;
import com.gustavo.hotel_management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private  final UserRepository userRepository;
    private  final RoomRepository roomRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public List<Reservation> ListAll() {

        return reservationRepository.findAll();


    }

    public Reservation lookFor(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrado"));
    }


    public Reservation update(Long id, Reservation reservation) {

         Reservation reservationedit = reservationRepository.findById(id)
                         .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

                reservationedit.setCheckInDate(reservation.getCheckInDate());
                reservationedit.setRoom(reservation.getRoom());
                reservationedit.setCheckOutDate(reservation.getCheckOutDate());
                reservationedit.setUser(reservation.getUser());




        return reservationRepository.save(reservationedit);
    }

    public Reservation create(Reservation reservation) {

        Long userId = reservation.getUser().getId();
        Long roomId = reservation.getRoom().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Habitacion no encontrado"));

        reservation.setUser(user);
        reservation.setRoom(room);

        return reservationRepository.save(reservation);

    }

    public void delete(Long id) {

        reservationRepository.deleteById(id);

    }
}
