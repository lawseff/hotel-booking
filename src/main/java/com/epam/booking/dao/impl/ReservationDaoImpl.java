package com.epam.booking.dao.impl;

import com.epam.booking.builder.Builder;
import com.epam.booking.dao.api.ReservationDao;
import com.epam.booking.entity.User;
import com.epam.booking.entity.reservation.Reservation;
import com.epam.booking.entity.reservation.ReservationStatus;
import com.epam.booking.entity.room.Room;
import com.epam.booking.entity.room.RoomClass;
import com.epam.booking.exception.DaoException;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class ReservationDaoImpl extends AbstractDao<Reservation> implements ReservationDao {

    private static final String TABLE_NAME = "reservation";
    private static final String SELECT_QUERY = "SELECT res.*, " +
            "   u.email, u.is_admin, u.user_password, u.first_name, u.second_name, " +
            "   c.class_name, c.basic_rate, c.rate_per_person, " +
            "   r.is_active, r.beds_amount " +
            "FROM reservation AS res " +
            "JOIN booking_user AS u ON res.user_id=u.id " +
            "JOIN room_class AS c ON res.room_class_id=c.id " +
            "LEFT JOIN room AS r ON res.room_id=r.id";
    private static final String FIND_BY_ID_QUERY = SELECT_QUERY + ' ' + "WHERE res.id = ?;";
    private static final String FIND_BY_USER_ID_QUERY = SELECT_QUERY + ' ' + "WHERE u.id = ?;";
    private static final String SAVE_QUERY = "INSERT INTO reservation " +
            "   (id, user_id, room_class_id, room_id, reservation_status, " +
            "   arrival_date, departure_date, persons_amount, total_price) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE  " +
            "   user_id=VALUES(user_id), " +
            "   room_class_id=VALUES(room_class_id), " +
            "   room_id=VALUES(room_id), " +
            "   reservation_status=VALUES(reservation_status), " +
            "   arrival_date=VALUES(arrival_date), " +
            "   departure_date=VALUES(departure_date), " +
            "   persons_amount=VALUES(persons_amount), " +
            "   total_price=VALUES(total_price);";

    public ReservationDaoImpl(Builder<Reservation> builder, Connection connection) {
        super(builder, connection);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Optional<Reservation> findById(int id) throws DaoException {
        return executeForSingleResult(FIND_BY_ID_QUERY, id);
    }

    @Override
    public List<Reservation> findByUserId(int id) throws DaoException {
        return executeQuery(FIND_BY_USER_ID_QUERY, id);
    }

    @Override
    public List<Reservation> getAll() throws DaoException {
        return executeQuery(SELECT_QUERY);
    }

    @Override
    public void save(Reservation entity) throws DaoException {
        User user = entity.getUser();
        RoomClass roomClass = entity.getRoomClass();
        Room room = entity.getRoom();
        ReservationStatus reservationStatus = entity.getReservationStatus();
        Object[] parameters = {
                entity.getId(),
                user.getId(),
                roomClass.getId(),
                (room != null) ? room.getId() : null,
                reservationStatus.toString(),
                entity.getArrivalDate(),
                entity.getDepartureDate(),
                entity.getPersonsAmount(),
                entity.getTotalPrice()
        };
        executeUpdate(SAVE_QUERY, parameters);
    }

    @Override
    public void deleteById(int id) throws DaoException {
        throw new UnsupportedOperationException();
    }

}
