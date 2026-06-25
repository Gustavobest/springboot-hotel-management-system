package com.gustavo.hotel_management.repository;

import com.gustavo.hotel_management.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository <Reservation, Long > , JpaSpecificationExecutor<Reservation> {
    @Query("""
        SELECT COUNT(r) > 0
        FROM Reservation r
        WHERE r.room.id = :roomId
        AND (
            (:checkIn BETWEEN r.checkInDate AND r.checkOutDate)
            OR
            (:checkOut BETWEEN r.checkInDate AND r.checkOutDate)
        )
    """)
    boolean existsOverlappingReservation(
            @Param("roomId") Long roomId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut
    );

    List<Reservation> findAllByRoomId(Long roomId);

    @Query("""
    SELECT COUNT(r) > 0
    FROM Reservation r
    WHERE r.id <> :reservationId
    AND r.room.id = :roomId
    AND (
        (:checkIn BETWEEN r.checkInDate AND r.checkOutDate)
        OR
        (:checkOut BETWEEN r.checkInDate AND r.checkOutDate)
    )
""")
    boolean existsOverlappingReservationForUpdate(
            @Param("reservationId") Long reservationId,
            @Param("roomId") Long roomId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut
    );


}
