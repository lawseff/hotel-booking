package com.epam.booking.dao.api;

import com.epam.booking.entity.reservation.Reservation;
import com.epam.booking.exception.DaoException;
import java.util.List;

public interface ReservationDao extends Dao<Reservation> {

    List<Reservation> findByUserId(int id) throws DaoException;

}
