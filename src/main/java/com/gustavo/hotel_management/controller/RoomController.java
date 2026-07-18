package com.gustavo.hotel_management.controller;

import com.gustavo.hotel_management.dto.RoomRequestDTO;
import com.gustavo.hotel_management.dto.RoomResponseDTO;
import com.gustavo.hotel_management.service.ReservationService;
import com.gustavo.hotel_management.service.RoomService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.gustavo.hotel_management.entity.Room;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@SecurityRequirement(name = "bearerAuth")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }




    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomResponseDTO> createRoom(@RequestBody RoomRequestDTO request)
    {
            RoomResponseDTO  roonresponse = roomService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roonresponse);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<RoomResponseDTO>> listRoom()
    {
        return ResponseEntity.ok( roomService.ListRoom());

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomResponseDTO> editRoom( @PathVariable  Long id , @RequestBody  RoomRequestDTO room)
    {
        RoomResponseDTO roomresponse = roomService.editRoom(id , room);

        return ResponseEntity.ok(roomresponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletedRoom(@PathVariable Long id){
            roomService.deleteRoom(id);
        return  ResponseEntity.noContent().build();

    }
    @GetMapping("/page")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public  ResponseEntity<Page<RoomResponseDTO>> getRoomPaginated
            (@RequestParam(defaultValue = "0") int page , @RequestParam(defaultValue = "5") int size)
    {
        return ResponseEntity.ok(roomService.findAllPaginated(page , size));
    }
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Page<RoomResponseDTO>> searchRooms(@RequestParam(required = false) String name ,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "5") int size)
    {
        return ResponseEntity.ok(
                roomService.searchRooms(name,page,size)
        );
    }
}
