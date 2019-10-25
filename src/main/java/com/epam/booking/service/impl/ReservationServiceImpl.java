package com.epam.booking.service.impl;

import com.epam.booking.builder.Builder;
import com.epam.booking.dao.DaoHelper;
import com.epam.booking.dao.api.ReservationDao;
import com.epam.booking.entity.User;
import com.epam.booking.entity.reservation.Reservation;
import com.epam.booking.entity.reservation.ReservationStatus;
import com.epam.booking.entity.room.Room;
import com.epam.booking.entity.room.RoomClass;
import com.epam.booking.exception.DaoException;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.ReservationService;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ReservationServiceImpl implements ReservationService {

    private ReservationDao dao;

    public ReservationServiceImpl(DaoHelper daoHelper, Builder<Reservation> builder) {
        dao = daoHelper.reservationDao(builder);
    }

    @Override
    public List<Reservation> getAllReservations() throws ServiceException {
        try {
            return dao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Reservation> findById(int id) throws ServiceException {
        try {
            return dao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Reservation> findByUserId(int userId) throws ServiceException {
        try {
            return dao.findByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void book(User user, Date arrivalDate, Date departureDate, RoomClass roomClass, int personsAmount)
            throws ServiceException {
        Room room  = null;
        BigDecimal totalPrice = null;
        Reservation reservation = new Reservation(user,
                roomClass, room,
                ReservationStatus.WAITING,
                arrivalDate, departureDate,
                personsAmount, totalPrice);
        try {
            dao.save(reservation);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }



    @Override
    public void approve(int id, Room room, BigDecimal totalPrice) throws ServiceException {
        Reservation reservation = getById(id);
        ReservationStatus reservationStatus = reservation.getReservationStatus();
        if (reservationStatus != ReservationStatus.WAITING) {
            throw new ServiceException("Can't approve reservation which is " + reservationStatus);
        }
        reservation.setRoom(room);
        reservation.setTotalPrice(totalPrice);
        reservation.setReservationStatus(ReservationStatus.APPROVED);
        try {
            dao.save(reservation);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void setPaid(int id) throws ServiceException {
        Reservation reservation = getById(id);
        ReservationStatus reservationStatus = reservation.getReservationStatus();
        if (reservationStatus != ReservationStatus.APPROVED) {
            throw new ServiceException("Can't set paid reservation which is " + reservationStatus);
        }
        reservation.setReservationStatus(ReservationStatus.PAID);
        try {
            dao.save(reservation);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void setCheckedIn(int id) throws ServiceException {
        Reservation reservation = getById(id);
        ReservationStatus reservationStatus = reservation.getReservationStatus();
        if (reservationStatus != ReservationStatus.PAID) {
            throw new ServiceException("Can't set checked in reservation which is " + reservationStatus);
        }
        reservation.setReservationStatus(ReservationStatus.CHECKED_IN);
        try {
            dao.save(reservation);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void setCheckedOut(int id) throws ServiceException {
        Reservation reservation = getById(id);
        ReservationStatus reservationStatus = reservation.getReservationStatus();
        if (reservationStatus != ReservationStatus.CHECKED_IN) {
            throw new ServiceException("Can't set checked out reservation which is " + reservationStatus);
        }
        reservation.setReservationStatus(ReservationStatus.CHECKED_OUT);
        try {
            dao.save(reservation);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void cancel(int id) throws ServiceException {
        try {
            Optional<Reservation> optional = dao.findById(id);
            if (!optional.isPresent()) {
                throw new ServiceException("Reservation not found by id: " + id);
            }
            Reservation reservation = optional.get();
            ReservationStatus reservationStatus = reservation.getReservationStatus();
            if (reservationStatus == ReservationStatus.CANCELLED || reservationStatus == ReservationStatus.CHECKED_OUT) {
                throw new ServiceException("Can't cancel reservation which is " + reservationStatus);
            }
            reservation.setReservationStatus(ReservationStatus.CANCELLED);
            dao.save(reservation);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private Reservation getById(int id) throws ServiceException {
        try {
            Optional<Reservation> optional = dao.findById(id);
            if (!optional.isPresent()) {
                throw new ServiceException("Reservation not found by id: " + id);
            }
            return optional.get();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
