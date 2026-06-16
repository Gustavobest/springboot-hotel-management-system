package com.gustavo.hotel_management.controller;

import com.gustavo.hotel_management.service.RoomService;
import org.springframework.web.bind.annotation.*;
import com.gustavo.hotel_management.entity.Room;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }




    @PostMapping
    public Room createRoom(@RequestBody Room room){

        return roomService.save(room);
    }

    @GetMapping
    public List<Room> listRoom(){
        return roomService.ListRoom();
    }

    @PutMapping("/{id}")
    public Room editRoom( @PathVariable  Long id , @RequestBody  Room room){
        return roomService.editRoom(id , room);
    }

    @DeleteMapping("/{id}")
    public void deletedRoom(@PathVariable Long id){
        roomService.deleteRoom(id);
    }

}
