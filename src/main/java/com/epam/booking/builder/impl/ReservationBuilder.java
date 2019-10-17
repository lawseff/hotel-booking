package com.epam.booking.builder.impl;

import com.epam.booking.builder.Builder;
import com.epam.booking.entity.User;
import com.epam.booking.entity.reservation.Reservation;
import com.epam.booking.entity.reservation.ReservationStatus;
import com.epam.booking.entity.room.Room;
import com.epam.booking.entity.room.RoomClass;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * An implementation of {@link Builder} for creating {@link Reservation} objects.
 *
 * @see Builder
 * @see Reservation
 */
public class ReservationBuilder implements Builder<Reservation> {

    private static final String ID_COLUMN = "id";
    private static final String RESERVATION_STATUS_COLUMN = "reservation_status";
    private static final String ARRIVAL_DATE_COLUMN = "arrival_date";
    private static final String DEPARTURE_DATE_COLUMN = "departure_date";
    private static final String PERSONS_AMOUNT_COLUMN = "persons_amount";
    private static final String TOTAL_PRICE_COLUMN = "total_price";

    private static final String USER_ID_ALIAS = "user_id";
    private static final String ROOM_CLASS_ID_ALIAS = "room_class_id";
    private static final String ROOM_ID_ALIAS = "room_id";

    private Builder<User> userBuilder;
    private Builder<RoomClass> roomClassBuilder;
    private Builder<Room> roomBuilder;

    public ReservationBuilder() {
        userBuilder = new UserBuilder(USER_ID_ALIAS);
        roomClassBuilder = new RoomClassBuilder(ROOM_CLASS_ID_ALIAS);
        roomBuilder = new RoomBuilder(ROOM_ID_ALIAS);
    }

    @Override
    public Reservation build(ResultSet resultSet) throws SQLException {
        Integer id = (Integer) resultSet.getObject(ID_COLUMN);
        if (resultSet.wasNull()) {
            return null;
        }
        User user = userBuilder.build(resultSet);
        RoomClass roomClass = roomClassBuilder.build(resultSet);
        Room room = roomBuilder.build(resultSet);
        String statusName = resultSet.getString(RESERVATION_STATUS_COLUMN);
        ReservationStatus reservationStatus = ReservationStatus.valueOf(statusName);
        Date arrivalDate = resultSet.getDate(ARRIVAL_DATE_COLUMN);
        Date departureDate = resultSet.getDate(DEPARTURE_DATE_COLUMN);
        int personsAmount = resultSet.getInt(PERSONS_AMOUNT_COLUMN);
        BigDecimal totalPrice = resultSet.getBigDecimal(TOTAL_PRICE_COLUMN);
        return new Reservation(id, user,
                roomClass, room, reservationStatus, arrivalDate, departureDate, personsAmount, totalPrice);
    }

}
