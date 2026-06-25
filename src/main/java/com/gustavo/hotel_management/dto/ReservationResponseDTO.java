package com.gustavo.hotel_management.dto;

import java.time.LocalDate;

public class ReservationResponseDTO {

    private Long id;
    private String userName;
    private String roomName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public ReservationResponseDTO() {
    }

    public ReservationResponseDTO(Long id, String roomName, LocalDate checkInDate, String userName, LocalDate checkOutDate) {
        this.id = id;
        this.roomName = roomName;
        this.checkInDate = checkInDate;
        this.userName = userName;
        this.checkOutDate = checkOutDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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
