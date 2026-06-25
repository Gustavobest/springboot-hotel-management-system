package com.gustavo.hotel_management.service;

import com.gustavo.hotel_management.dto.RoomRequestDTO;
import com.gustavo.hotel_management.dto.RoomResponseDTO;
import com.gustavo.hotel_management.entity.Room;
import com.gustavo.hotel_management.exception.RoomNotFoundException;
import com.gustavo.hotel_management.repository.RoomRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoomService {

   private final RoomRepository roomRepository;


    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    @Transactional
    public RoomResponseDTO save(RoomRequestDTO request){

        Room room = new Room();
        room.setName(request.getName());
        room.setPrice(request.getPrice());

        Room savedRoom = roomRepository.save(room);

        return new RoomResponseDTO(
                savedRoom.getId(),
                savedRoom.getName(),
                savedRoom.getPrice()

        );
    }
    @Transactional(readOnly = true)
    public List<RoomResponseDTO> ListRoom(){

         List<Room> lista = roomRepository.findAll();

           return  lista.stream()
                   .map(room ->  new RoomResponseDTO(
                           room.getId(),
                           room.getName(),
                           room.getPrice()
                   ))
                   .toList();


    }
    @Transactional
    public RoomResponseDTO editRoom(Long id , RoomRequestDTO roomDto){
        Room roomExist = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));

        roomExist.setName(roomDto.getName());
        roomExist.setPrice(roomDto.getPrice());
        Room roomEdit = roomRepository.save(roomExist);

        return new RoomResponseDTO(
                roomEdit.getId(),
                roomEdit.getName(),
                roomEdit.getPrice()
        );


    }
    @Transactional
    public void deleteRoom( Long id)
    {
        Room room = roomRepository.findById(id)
                .orElseThrow(() ->  new RoomNotFoundException(id));

        if(!room.getReservations().isEmpty()){
            throw new RuntimeException(
                    "No se puede eliminar una habitación con reservas"
            );
        }
           roomRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public Page<RoomResponseDTO> findAllPaginated(int page, int size) {

                         Pageable pageable =  PageRequest.of(page,size);
                       return roomRepository.findAll(pageable)
                               .map(room -> new RoomResponseDTO(
                                       room.getId(),
                                       room.getName(),
                                       room.getPrice()

                               ));

    }
}
