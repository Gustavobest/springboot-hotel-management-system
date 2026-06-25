package com.gustavo.hotel_management.controller;

import com.gustavo.hotel_management.dto.ReservationRequestDTO;
import com.gustavo.hotel_management.dto.ReservationResponseDTO;
import com.gustavo.hotel_management.entity.Reservation;
import com.gustavo.hotel_management.service.ReservationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@SecurityRequirement(name = "bearerAuth")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or  hasRole('USER')")
    public ResponseEntity<List<ReservationResponseDTO>> ListAll(){
        return ResponseEntity.ok(
                reservationService.ListAll()
        );
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservationResponseDTO> lookFor(@PathVariable Long id){
        ReservationResponseDTO resevation = reservationService.lookFor(id);

        return ResponseEntity.ok(resevation);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservationResponseDTO> update( @PathVariable Long id ,@Valid @RequestBody ReservationRequestDTO reservation)
    {

        ReservationResponseDTO reservationin = reservationService.update(id, reservation);

        return  ResponseEntity.ok(reservationin);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ReservationResponseDTO> create(@RequestBody ReservationRequestDTO reservationDto)
    {
                       ReservationResponseDTO  reservationcreate = reservationService.create(reservationDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservationcreate);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<Void> delete(@PathVariable Long id)
    {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Page<ReservationResponseDTO>> getReservationPaginated
            (@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size)
    {
        return ResponseEntity.ok(reservationService.findAllPaginated(page , size));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ReservationResponseDTO>> searchReservattions (
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String roomName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        return ResponseEntity.ok(
                reservationService.searchReservations(
                        userName,
                        roomName,
                        page,
                        size
                )
        );


    }



}
