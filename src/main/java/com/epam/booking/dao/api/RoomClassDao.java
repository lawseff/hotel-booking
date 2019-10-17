package com.epam.booking.dao.api;

import com.epam.booking.entity.room.RoomClass;
import com.epam.booking.exception.DaoException;
import java.util.Optional;

public interface RoomClassDao extends Dao<RoomClass> {

    Optional<RoomClass> findByName(String name) throws DaoException;

}
