package com.gustavo.hotel_management.mapper;

import com.gustavo.hotel_management.dto.ReservationResponseDTO;
import com.gustavo.hotel_management.entity.Reservation;

public class ReservationMapper {


    public static ReservationResponseDTO toDTO(Reservation reservation) {
        return new ReservationResponseDTO(
                reservation.getId(),
                reservation.getRoom().getName(),
                reservation.getCheckInDate(),
                reservation.getUser().getName(),
                reservation.getCheckOutDate()
        );
    }
}
