package com.epam.booking.service.impl;

import com.epam.booking.builder.Builder;
import com.epam.booking.dao.DaoHelper;
import com.epam.booking.dao.api.RoomDao;
import com.epam.booking.entity.room.Room;
import com.epam.booking.exception.DaoException;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.RoomService;
import java.util.List;
import java.util.Optional;

public class RoomServiceImpl implements RoomService {

    private RoomDao dao;

    public RoomServiceImpl(DaoHelper daoHelper, Builder<Room> builder) {
        this.dao = daoHelper.roomDao(builder);
    }

    @Override
    public Optional<Room> findById(int id) throws ServiceException {
        try {
            return dao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Room> getAllRooms() throws ServiceException {
        try {
            return dao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void setActiveById(int id, boolean active) throws ServiceException {
        try {
            Optional<Room> optional = dao.findById(id);
            if (!optional.isPresent()) {
                throw new ServiceException("Room not found by id=" + id);
            }
            Room room = optional.get();
            room.setActive(active);
            dao.save(room);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
