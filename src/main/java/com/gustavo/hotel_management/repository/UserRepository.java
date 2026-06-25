package com.gustavo.hotel_management.repository;

import com.gustavo.hotel_management.dto.UserRequestDTO;
import com.gustavo.hotel_management.dto.UserResponseDTO;
import com.gustavo.hotel_management.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> , JpaSpecificationExecutor<User> {



    Optional<User> findByEmail(String email);





}
