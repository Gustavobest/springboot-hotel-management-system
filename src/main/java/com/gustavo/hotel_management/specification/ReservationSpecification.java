package com.gustavo.hotel_management.specification;

import com.gustavo.hotel_management.entity.Reservation;
import org.springframework.data.jpa.domain.Specification;

public class ReservationSpecification {

    public  static Specification<Reservation> hasUserName (String userName)
    {
        return (root , query , cb) ->{
            if(userName == null || userName.isBlank()){
                return null;
            }
            return cb.like(
                    cb.lower(root.get("user").get("name")),
                    "%" + userName.toLowerCase() + "%"
            );
        };
    }
   public static Specification<Reservation> hasRoomName (String roomName){
        return (root  , query , cb) ->{
            if(roomName == null || roomName.isBlank()){
                return null;
            }
            return cb.like(
                    cb.lower(root.get("room").get("name")),
                    "%" + roomName.toLowerCase() + "%"
            );
        };
   }


}
