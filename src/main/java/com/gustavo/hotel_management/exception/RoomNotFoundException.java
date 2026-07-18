package com.gustavo.hotel_management.exception;

public class RoomNotFoundException extends RuntimeException
{
    public RoomNotFoundException(Long id )
    {
        super("Habitacion no encontrada con id : " + id);
    }
}
