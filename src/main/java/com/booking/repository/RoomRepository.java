package com.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.booking.entity.room.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

}
