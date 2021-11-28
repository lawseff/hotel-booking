package com.epam.booking.dao;

import com.epam.booking.builder.Builder;
import com.epam.booking.dao.api.ReservationDao;
import com.epam.booking.dao.api.RoomClassDao;
import com.epam.booking.dao.api.RoomDao;
import com.epam.booking.dao.api.UserDao;
import com.epam.booking.dao.impl.ReservationDaoImpl;
import com.epam.booking.dao.impl.RoomClassDaoImpl;
import com.epam.booking.dao.impl.RoomDaoImpl;
import com.epam.booking.dao.impl.UserDaoImpl;
import web.entity.User;
import web.entity.reservation.Reservation;
import web.entity.room.Room;
import web.entity.room.RoomClass;
import com.epam.booking.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoHelper {

    private Connection connection;

    public DaoHelper(Connection connection) {
        this.connection = connection;
    }

    public UserDao userDao(Builder<User> builder) {
        return new UserDaoImpl(builder, connection);
    }

    public RoomClassDao roomClassDao(Builder<RoomClass> builder) {
        return new RoomClassDaoImpl(builder, connection);
    }

    public RoomDao roomDao(Builder<Room> builder) {
        return new RoomDaoImpl(builder, connection);
    }

    public ReservationDao reservationDao(Builder<Reservation> builder) {
        return new ReservationDaoImpl(builder, connection);
    }

    public void startTransaction() throws DaoException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public void endTransaction() throws DaoException {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public void cancelTransaction() throws DaoException {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

}
