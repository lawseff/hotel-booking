package com.epam.booking.service.api;

import com.epam.booking.entity.room.Room;
import com.epam.booking.exception.ServiceException;
import java.util.List;
import java.util.Optional;

public interface RoomService {

    List<Room> getAllRooms() throws ServiceException;
    void setActiveById(int id, boolean active) throws ServiceException;
    Optional<Room> findById(int id) throws ServiceException;

}
