package com.gustavo.hotel_management.specification;

import com.gustavo.hotel_management.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> hasName(String name){
        return (root , query , cb) -> {
            if (name == null || name.isEmpty()) return null;
            return  cb.like(
                    cb.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%"
            );
        };
    }



}
