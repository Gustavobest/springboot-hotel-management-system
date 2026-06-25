package com.gustavo.hotel_management.validation;
import com.gustavo.hotel_management.entity.Room;
import com.gustavo.hotel_management.exception.RoomNotFoundException;
import com.gustavo.hotel_management.repository.RoomRepository;
import org.springframework.stereotype.Component;

@Component
public class RoomValidator {
    private final RoomRepository roomRepository;

    public RoomValidator(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room validateRoomExists(Long roomId){
        return  roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));
    }



}
