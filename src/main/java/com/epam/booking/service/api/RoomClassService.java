package com.epam.booking.service.api;

import com.epam.booking.entity.room.RoomClass;
import com.epam.booking.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RoomClassService {

    List<RoomClass> getAllRoomClasses() throws ServiceException;
    Optional<RoomClass> findByName(String name) throws ServiceException;
    void updatePrices(List<RoomClass> roomClasses) throws ServiceException;

}
