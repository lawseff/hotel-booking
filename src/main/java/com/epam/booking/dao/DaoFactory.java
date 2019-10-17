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
import com.epam.booking.entity.User;
import com.epam.booking.entity.reservation.Reservation;
import com.epam.booking.entity.room.Room;
import com.epam.booking.entity.room.RoomClass;
import java.sql.Connection;

public class DaoFactory {

    private Connection connection;

    public DaoFactory(Connection connection) {
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

}
