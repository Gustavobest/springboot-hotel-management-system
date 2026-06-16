package com.gustavo.hotel_management.service;

import com.gustavo.hotel_management.entity.Room;
import com.gustavo.hotel_management.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class RoomService {

   private final RoomRepository roomRepository;


    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room save(Room room){
        return roomRepository.save(room);
    }

    public List<Room> ListRoom(){
        return roomRepository.findAll();
    }
    public Room editRoom(Long id , Room room){
        Room roomExist = roomRepository.findById(id)
                .orElseThrow();

        roomExist.setName(room.getName());
        roomExist.setPrice(room.getPrice());

        return roomRepository.save(roomExist);


    }
    public void deleteRoom( Long id)
    {
           roomRepository.deleteById(id);
    }

}
