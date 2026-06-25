package com.gustavo.hotel_management.mapper;

import com.gustavo.hotel_management.dto.RoomResponseDTO;
import com.gustavo.hotel_management.entity.Room;

public class RoomMapper {
    public static RoomResponseDTO toDTO(Room room){
        return new RoomResponseDTO(
                room.getId(),
                room.getName(),
                room.getPrice()
        );
    }
}
