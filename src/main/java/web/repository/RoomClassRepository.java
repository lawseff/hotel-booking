package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.entity.room.RoomClass;

@Repository
public interface RoomClassRepository extends JpaRepository<RoomClass, Integer> {

}
