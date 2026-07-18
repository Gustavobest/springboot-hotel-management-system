package com.gustavo.hotel_management.exception;
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id )
    {

        super("Usuario no encontrado con id :" + id);

    }




}
