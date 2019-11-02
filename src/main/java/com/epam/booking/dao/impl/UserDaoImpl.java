package com.epam.booking.dao.impl;

import com.epam.booking.builder.Builder;
import com.epam.booking.dao.api.UserDao;
import com.epam.booking.entity.User;
import com.epam.booking.exception.DaoException;
import java.sql.Connection;
import java.util.Optional;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    private static final String TABLE_NAME = "booking_user";
    private static final String FIND_BY_EMAIL_AND_PASSWORD_QUERY =
            "SELECT * FROM booking_user WHERE email=? AND user_password=?;";

    public UserDaoImpl(Builder<User> builder, Connection connection) {
        super(builder, connection);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Optional<User> getByEmailAndPassword(String email, String password) throws DaoException {
        return executeForSingleResult(
                FIND_BY_EMAIL_AND_PASSWORD_QUERY,
                email, password);
    }

    @Override
    public void save(User entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(int id) throws DaoException {
        throw new UnsupportedOperationException();
    }

}
