package com.booking.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.booking.entity.room.RoomClass;

@Repository
public interface RoomClassRepository extends JpaRepository<RoomClass, Integer> {

    Optional<RoomClass> findByName(String name);

}
