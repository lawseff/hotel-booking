package com.epam.booking.service.api;

import com.epam.booking.entity.User;
import com.epam.booking.entity.reservation.Reservation;
import com.epam.booking.entity.room.Room;
import com.epam.booking.entity.room.RoomClass;
import com.epam.booking.exception.ServiceException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReservationService {

    List<Reservation> getAllReservations() throws ServiceException;

    Optional<Reservation> getById(int id) throws ServiceException;

    List<Reservation> getByUserId(int userId) throws ServiceException;

    void book(User user, Date arrivalDate, Date departureDate, RoomClass roomClass, int personsAmount)
            throws ServiceException;

    void approve(int id, Room room, BigDecimal totalPrice) throws ServiceException;

    void setPaid(int id) throws ServiceException;

    void setCheckedIn(int id) throws ServiceException;

    void setCheckedOut(int id) throws ServiceException;

    void cancel(int id) throws ServiceException;

}
