package com.gustavo.hotel_management.mapper;
import com.gustavo.hotel_management.dto.UserResponseDTO;
import com.gustavo.hotel_management.entity.User;

public class UserMapper {

   public  static UserResponseDTO toDTO(User user){
       return new UserResponseDTO(
                user.getId(),
               user.getName(),
               user.getEmail(),
               user.getRole()
       );

   }

}
