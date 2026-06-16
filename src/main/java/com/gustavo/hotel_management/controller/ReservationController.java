package com.gustavo.hotel_management.controller;

import com.gustavo.hotel_management.entity.Reservation;
import com.gustavo.hotel_management.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> ListAll(){
        return reservationService.ListAll();
    }


    @GetMapping("/{id}")
    public  Reservation lookFor(@PathVariable Long id){
        return reservationService.lookFor(id);
    }

    @PutMapping("/{id}")
    public Reservation update( @PathVariable Long id ,@Valid Reservation reservation)
    {
        return reservationService.update(id, reservation);
    }

    @PostMapping
    public Reservation create(@RequestBody Reservation reservation)
    {
        return reservationService.create(reservation);
    }

    @DeleteMapping("/{id}")
    public  void delete(@PathVariable Long id)
    {
        reservationService.delete(id);
    }
}
