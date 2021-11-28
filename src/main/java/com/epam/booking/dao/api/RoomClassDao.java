package com.epam.booking.dao.api;

import web.entity.room.RoomClass;
import com.epam.booking.exception.DaoException;
import java.util.Optional;

public interface RoomClassDao extends Dao<RoomClass> {

    Optional<RoomClass> getByName(String name) throws DaoException;

}
