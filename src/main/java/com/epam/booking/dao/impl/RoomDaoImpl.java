package com.epam.booking.dao.impl;

import com.epam.booking.builder.Builder;
import com.epam.booking.dao.api.RoomDao;
import com.epam.booking.entity.room.Room;
import com.epam.booking.entity.room.RoomClass;
import com.epam.booking.exception.DaoException;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class RoomDaoImpl extends AbstractDao<Room> implements RoomDao {

    private static final String TABLE_NAME = "room";
    private static final String FIND_BY_ID_QUERY = "SELECT r.*, c.class_name, c.basic_rate, c.rate_per_person " +
            "FROM room AS r JOIN room_class AS c ON r.room_class_id=c.id WHERE r.id=?";
    private static final String GET_ALL_QUERY = "SELECT r.*, c.class_name, c.basic_rate, c.rate_per_person " +
            "FROM room AS r JOIN room_class AS c ON r.room_class_id=c.id;";
    private static final String SAVE_QUERY = "INSERT INTO room " +
            "(id, is_active, room_class_id, beds_amount) " +
            "VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE " +
            "is_active=VALUES(is_active)," +
            "room_class_id=VALUES(room_class_id)," +
            "beds_amount=VALUES(beds_amount);";

    public RoomDaoImpl(Builder<Room> builder, Connection connection) {
        super(builder, connection);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Optional<Room> getById(int id) throws DaoException {
        return executeForSingleResult(FIND_BY_ID_QUERY, id);
    }

    @Override
    public List<Room> getAll() throws DaoException {
        return executeQuery(GET_ALL_QUERY);
    }

    @Override
    public void save(Room entity) throws DaoException {
        RoomClass roomClass = entity.getRoomClass();
        Object[] parameters = {
                entity.getId(),
                entity.isActive() ? 1 : 0,
                roomClass.getId(),
                entity.getBedsAmount()
        };
        executeUpdate(SAVE_QUERY, parameters);
    }

    @Override
    public void deleteById(int id) throws DaoException {
        throw new UnsupportedOperationException();
    }

}
