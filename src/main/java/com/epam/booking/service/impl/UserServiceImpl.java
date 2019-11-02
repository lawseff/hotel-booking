package com.epam.booking.service.impl;

import com.epam.booking.builder.Builder;
import com.epam.booking.dao.DaoHelper;
import com.epam.booking.dao.api.UserDao;
import com.epam.booking.entity.User;
import com.epam.booking.exception.DaoException;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.UserService;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private DaoHelper daoHelper;
    private Builder<User> builder;

    public UserServiceImpl(DaoHelper daoHelper, Builder<User> builder) {
        this.daoHelper = daoHelper;
        this.builder = builder;
    }

    @Override
    public Optional<User> getUserByEmailAndPassword(String email, String password) throws ServiceException {
        try {
            UserDao dao = daoHelper.userDao(builder);
            String encryptedPassword = DigestUtils.md5Hex(password);
            return dao.getByEmailAndPassword(email, encryptedPassword);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
