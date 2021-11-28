package com.epam.booking.dao.api;

import web.entity.reservation.Reservation;
import com.epam.booking.exception.DaoException;
import java.util.List;

public interface ReservationDao extends Dao<Reservation> {

    List<Reservation> getByUserId(int id) throws DaoException;

}
