package com.gustavo.hotel_management.exception;

public class RoomHasReservationsException extends RuntimeException{
    public RoomHasReservationsException(Long id){
        super("Room with ID" + "cannot be deleted because it has active reservations");
    }
}
