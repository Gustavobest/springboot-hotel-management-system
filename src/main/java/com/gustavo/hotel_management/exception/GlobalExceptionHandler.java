package com.gustavo.hotel_management.exception;
import com.gustavo.hotel_management.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex) { //

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage())); //

        return errors; //
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException( Exception ex){

         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                 .body(new ErrorResponseDTO(
                         "Unexpect error ocurred",
                         500
                 ));


    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFound(UserNotFoundException ex)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.value()
                ));


    }
    @ExceptionHandler(RoomNotFoundException.class)
    public  ResponseEntity<ErrorResponseDTO> handleRoomNotFound(RoomNotFoundException ex)
    {
        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.value()
                ));
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlReservacionNotFound(ReservationNotFoundException ex)
    {
        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.value()
                ));

    }
    @ExceptionHandler(RoomHasReservationsException.class)
    public ResponseEntity<ErrorResponseDTO> handleRoomHasReservations(RoomHasReservationsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(ex.getMessage(),HttpStatus.CONFLICT.value()));
    }

























}