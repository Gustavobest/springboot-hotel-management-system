package com.gustavo.hotel_management.entity;

import com.gustavo.hotel_management.model.ReservationStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;


    public Reservation( LocalDate checkInDate, User user, LocalDate checkOutDate, Room room) {
        this.checkInDate = checkInDate;
        this.user = user;
        this.checkOutDate = checkOutDate;
        this.room = room;
    }

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {return room;}

    public void setRoom(Room room) {
        this.room = room;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public void initialize(){
        if(this.status == null){
            this.status = ReservationStatus.PENDING;
        }
    }
}
