package com.epam.booking.service.impl;

import com.epam.booking.builder.Builder;
import com.epam.booking.dao.DaoFactory;
import com.epam.booking.dao.api.RoomClassDao;
import com.epam.booking.entity.room.RoomClass;
import com.epam.booking.exception.DaoException;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.RoomClassService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class RoomClassServiceImpl implements RoomClassService {

    private RoomClassDao dao;

    public RoomClassServiceImpl(DaoFactory daoFactory, Builder<RoomClass> builder) {
        dao = daoFactory.roomClassDao(builder);
    }

    @Override
    public List<RoomClass> getAllRoomClasses() throws ServiceException {
        try {
            return dao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<RoomClass> findByName(String name) throws ServiceException {
        try {
            return dao.findByName(name);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updatePrices(int id, BigDecimal basicRate, BigDecimal ratePerPerson) throws ServiceException {
        try {
            Optional<RoomClass> optional = dao.findById(id);
            if (!optional.isPresent()) {
                throw new ServiceException("RoomClass not found by id=" + id);
            }
            RoomClass roomClass = optional.get();
            roomClass.setBasicRate(basicRate);
            roomClass.setRatePerPerson(ratePerPerson);
            dao.save(roomClass);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
