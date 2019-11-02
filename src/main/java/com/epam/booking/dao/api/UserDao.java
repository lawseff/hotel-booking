package com.epam.booking.dao.api;

import com.epam.booking.entity.User;
import com.epam.booking.exception.DaoException;
import java.util.Optional;

public interface UserDao extends Dao<User> {

    Optional<User> getByEmailAndPassword(String email, String password) throws DaoException;

}
