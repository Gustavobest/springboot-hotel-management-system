package com.gustavo.hotel_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ReservationRequestDTO {

    @NotNull(message = "el usuario es obligatoria")
    private Long userId;
    @NotNull(message = "La habitacion es obligatoria")
    private Long roomId;
    @NotNull(message = "La fecha de ingresa es obligatoria")
    private LocalDate checkInDate;
    @NotNull(message = "La fecha de salio es obligatoria")
    private LocalDate checkOutDate;

    public ReservationRequestDTO() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}
