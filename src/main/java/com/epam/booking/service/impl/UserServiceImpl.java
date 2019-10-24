package com.epam.booking.service.impl;

import com.epam.booking.builder.Builder;
import com.epam.booking.dao.DaoFactory;
import com.epam.booking.dao.api.UserDao;
import com.epam.booking.entity.User;
import com.epam.booking.exception.DaoException;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.UserService;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private DaoFactory daoFactory;
    private Builder<User> builder;

    public UserServiceImpl(DaoFactory daoFactory, Builder<User> builder) {
        this.daoFactory = daoFactory;
        this.builder = builder;
    }

    @Override
    public Optional<User> login(String email, String password) throws ServiceException {
        try {
            UserDao dao = daoFactory.userDao(builder);
            String encryptedPassword = DigestUtils.md5Hex(password);
            return dao.findByEmailAndPassword(email, encryptedPassword);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
