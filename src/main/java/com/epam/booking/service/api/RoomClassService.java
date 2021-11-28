package com.epam.booking.service.api;

import web.entity.room.RoomClass;
import com.epam.booking.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface RoomClassService {

    List<RoomClass> getAllRoomClasses() throws ServiceException;
    Optional<RoomClass> getByName(String name) throws ServiceException;
    void updatePrices(List<RoomClass> roomClasses) throws ServiceException;

}
