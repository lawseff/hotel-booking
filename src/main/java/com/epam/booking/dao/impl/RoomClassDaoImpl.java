package com.epam.booking.dao.impl;

import com.epam.booking.builder.Builder;
import com.epam.booking.dao.api.RoomClassDao;
import web.entity.room.RoomClass;
import com.epam.booking.exception.DaoException;
import java.sql.Connection;
import java.util.Optional;

public class RoomClassDaoImpl extends AbstractDao<RoomClass> implements RoomClassDao {

    private static final String TABLE_NAME = "room_class";
    private static final String FIND_BY_NAME_QUERY = "SELECT * FROM room_class WHERE class_name=?;";
    private static final String SAVE_QUERY = "INSERT INTO room_class (id, class_name, basic_rate, rate_per_person) " +
            "VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE class_name=VALUES(class_name), " +
                "basic_rate=VALUES(basic_rate), rate_per_person=VALUES(rate_per_person);";

    public RoomClassDaoImpl(Builder<RoomClass> builder, Connection connection) {
        super(builder, connection);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Optional<RoomClass> getByName(String name) throws DaoException {
        return executeForSingleResult(FIND_BY_NAME_QUERY, name);
    }

    @Override
    public void save(RoomClass entity) throws DaoException {
        Object[] parameters = {
                entity.getId(),
                entity.getName(),
                entity.getBasicRate(),
                entity.getRatePerPerson()
        };
        executeUpdate(SAVE_QUERY, parameters);
    }

    @Override
    public void deleteById(int id) throws DaoException {
        throw new UnsupportedOperationException();
    }

}
