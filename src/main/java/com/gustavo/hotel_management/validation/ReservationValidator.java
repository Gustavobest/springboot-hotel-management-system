package com.gustavo.hotel_management.validation;

import com.gustavo.hotel_management.exception.ReservationNotFoundException;
import com.gustavo.hotel_management.repository.ReservationRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;



@Component
public class ReservationValidator {

    private final ReservationRepository reservationRepository;

    public ReservationValidator(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void validateDates(LocalDate checkIn , LocalDate checkOut){
        if(checkIn.isBefore(LocalDate.now())){
            throw new RuntimeException("Check-in invalido");
        }
        if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)){
            throw new RuntimeException("Fecha invalidas");
        }
    }


    public void validateAvailabilityForUpdate(Long reservationId, Long roomId, LocalDate checkIn, LocalDate checkOut)
    {
        if(reservationRepository.existsOverlappingReservationForUpdate(
                reservationId,
                roomId,
                checkIn,
                checkOut

        )){
            throw new RuntimeException("la habitacion ya esta reservada en esas fechas");
        }




    }

    public void validateAvailabilityForCreate(Long roomId, LocalDate checkIn, LocalDate checkOut) {
       if(reservationRepository.existsOverlappingReservation(
               roomId,
               checkIn,
               checkOut
       )) {
           throw new RuntimeException(
                   "la habitacion ya esta reservada en esas fechas");

       }
    }
    public void validateAvailabilityForExists(Long id) {
        if (!reservationRepository.existsById(id)){
            throw new ReservationNotFoundException(id);
        }
    }




    }
