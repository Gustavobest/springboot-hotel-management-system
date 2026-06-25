package com.gustavo.hotel_management.specification;

import com.gustavo.hotel_management.entity.Room;
import org.springdoc.core.configuration.SpringDocSpecPropertiesConfiguration;
import org.springframework.data.jpa.domain.Specification;

public class RoomSpecification {
    public static Specification<Room>  hasName(String name)
    {
        return (root , query , cb) ->{
            if(name == null || name.isEmpty()) return null;
            return cb.like(
                    cb.lower(root.get("name")),
                    "%" + name.toLowerCase() +"%"
            );
        };
    }


}
