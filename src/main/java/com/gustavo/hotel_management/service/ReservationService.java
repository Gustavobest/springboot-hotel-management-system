package com.gustavo.hotel_management.service;

import com.gustavo.hotel_management.dto.ReservationRequestDTO;
import com.gustavo.hotel_management.dto.ReservationResponseDTO;
import com.gustavo.hotel_management.entity.Reservation;
import com.gustavo.hotel_management.entity.Room;
import com.gustavo.hotel_management.entity.User;
import com.gustavo.hotel_management.exception.ReservationNotFoundException;
import com.gustavo.hotel_management.exception.RoomNotFoundException;
import com.gustavo.hotel_management.exception.UserNotFoundException;
import com.gustavo.hotel_management.mapper.ReservationMapper;
import com.gustavo.hotel_management.repository.ReservationRepository;
import com.gustavo.hotel_management.repository.RoomRepository;
import com.gustavo.hotel_management.repository.UserRepository;
import com.gustavo.hotel_management.specification.ReservationSpecification;
import com.gustavo.hotel_management.validation.ReservationValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
@Slf4j
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private  final UserRepository userRepository;
    private  final RoomRepository roomRepository;
    private final ReservationValidator reservationValidator;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, RoomRepository roomRepository, ReservationValidator reservationValidator) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.reservationValidator = reservationValidator;
    }
    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> ListAll() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationMapper::toDTO
                ).toList();


    }
    @Transactional(readOnly = true)
    public ReservationResponseDTO lookFor(Long id) {
        Reservation reservationExist = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));



        return  ReservationMapper.toDTO(reservationExist);
    }

    @Transactional
    public ReservationResponseDTO update(Long id,
                                         ReservationRequestDTO reservationDto) {

        Reservation reservationEdit = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        Room room = roomRepository.findById(reservationDto.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException(reservationDto.getRoomId()));

        User user = userRepository.findById(reservationDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(reservationDto.getUserId()));

        reservationValidator.validateDates(
                reservationDto.getCheckInDate(),
                reservationDto.getCheckOutDate()
        );

        reservationValidator.validateAvailabilityForUpdate(
                id,
                room.getId(),
                reservationDto.getCheckInDate(),
                reservationDto.getCheckOutDate()
        );

        reservationEdit.setCheckInDate(reservationDto.getCheckInDate());
        reservationEdit.setCheckOutDate(reservationDto.getCheckOutDate());
        reservationEdit.setRoom(room);
        reservationEdit.setUser(user);

        Reservation updatedReservation = reservationRepository.save(reservationEdit);

        return ReservationMapper.toDTO(updatedReservation);
    }
     @Transactional
    public ReservationResponseDTO create(ReservationRequestDTO reservationDto) {


        Long roomId = reservationDto.getRoomId();
        LocalDate checkIn = reservationDto.getCheckInDate();
        LocalDate checkOut = reservationDto.getCheckOutDate();

        log.info("Creating reservation for user {} in room {}" ,reservationDto.getUserId(),reservationDto.getRoomId());
        User user = userRepository.findById(reservationDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(reservationDto.getUserId()));

        Room room = roomRepository.findById(reservationDto.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException(reservationDto.getRoomId()));



        reservationValidator.validateDates(checkIn, checkOut);
        reservationValidator.validateAvailabilityForCreate(roomId , checkIn ,checkOut);

        Reservation reservationToSave = new Reservation();
        reservationToSave.setCheckInDate(reservationDto.getCheckInDate());
        reservationToSave.setCheckOutDate(reservationDto.getCheckOutDate());
        reservationToSave.setUser(user);
        reservationToSave.setRoom(room);
         reservationToSave.initialize();
        Reservation reservationExist = reservationRepository.save(reservationToSave);
        log.info("Reservation {} created successfully", reservationExist.getId());

        return ReservationMapper.toDTO(reservationExist);

    }
    @Transactional
    public void delete(Long id) {
         reservationValidator.validateAvailabilityForExists(id);
         log.info("Deleting reservation{}", id);
        reservationRepository.deleteById(id);
        log.info("Reservation {} deleted successfully" , id);

    }
    @Transactional(readOnly = true)
    public Page<ReservationResponseDTO> findAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reservationRepository.findAll(pageable)
                .map(ReservationMapper::toDTO
                );

    }
    @Transactional(readOnly = true)
    public Page<ReservationResponseDTO> searchReservations(
            String userName,
            String roomName,
            int page,
            int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        log.debug("Searchin reservations {} + {}" , userName , roomName  );
        Specification<Reservation> spec = Specification
                .where(ReservationSpecification.hasUserName(userName))
                .and(ReservationSpecification.hasRoomName(roomName));

        Page<Reservation> reservations = reservationRepository.findAll(spec,pageable);
        return reservations.map(ReservationMapper::toDTO);
    }
}
