package com.gustavo.hotel_management.service;

import com.gustavo.hotel_management.dto.RoomRequestDTO;
import com.gustavo.hotel_management.dto.RoomResponseDTO;
import com.gustavo.hotel_management.entity.Room;
import com.gustavo.hotel_management.exception.RoomHasReservationsException;
import com.gustavo.hotel_management.exception.RoomNotFoundException;
import com.gustavo.hotel_management.mapper.RoomMapper;
import com.gustavo.hotel_management.repository.RoomRepository;
import com.gustavo.hotel_management.specification.RoomSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
@Slf4j
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

        log.info("Room successfully created with ID : {}" , savedRoom.getId());

        return RoomMapper.toDTO(savedRoom);
    }
    @Transactional(readOnly = true)
    public List<RoomResponseDTO> ListRoom(){

         List<Room> lista = roomRepository.findAll();

           return  lista.stream()
                   .map(RoomMapper::toDTO)
                   .toList();


    }

    @Transactional
    public RoomResponseDTO editRoom(Long id , RoomRequestDTO roomDto){
        Room roomExist = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));

        roomExist.setName(roomDto.getName());
        roomExist.setPrice(roomDto.getPrice());
        Room roomEdit = roomRepository.save(roomExist);
        log.info("Room with ID :{} successfully update" , id);
        return RoomMapper.toDTO(roomEdit);


    }
    @Transactional
    public void deleteRoom( Long id)
    {
        Room room = roomRepository.findById(id)
                .orElseThrow(() ->  new RoomNotFoundException(id));

        if(!room.getReservations().isEmpty()){
             log.warn("Delection denied: Room ID {} has active reservations associated",id);
            throw new RoomHasReservationsException(id);
        }
           roomRepository.deleteById(id);
           log.info("Room {} deleted successfully" , id);
    }
    @Transactional(readOnly = true)
    public Page<RoomResponseDTO> findAllPaginated(int page, int size) {

                         Pageable pageable =  PageRequest.of(page,size);
                       return roomRepository.findAll(pageable)
                               .map(RoomMapper::toDTO);

    }


    @Transactional(readOnly = true)
    public Page<RoomResponseDTO> searchRooms(String name , int page , int size){
    Pageable pageable = PageRequest.of(page,size);
         log.debug("Searching room {} ",name);
        Specification<Room> spec = Specification.where(
                RoomSpecification.hasName(name)
        );
        Page<Room> rooms = roomRepository.findAll(spec,pageable);

        return rooms.map(RoomMapper::toDTO);



    }





















}
