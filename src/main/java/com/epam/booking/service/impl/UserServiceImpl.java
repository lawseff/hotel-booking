package com.epam.booking.service.impl;

import com.epam.booking.builder.Builder;
import com.epam.booking.dao.DaoHelper;
import com.epam.booking.dao.api.UserDao;
import com.epam.booking.dto.SignUpRequest;
import com.epam.booking.entity.User;
import com.epam.booking.exception.DaoException;
import com.epam.booking.exception.EntityAlreadyExistsException;
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
            String encryptedPassword = encryptPassword(password);
            return dao.getByEmailAndPassword(email, encryptedPassword);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public User signUp(SignUpRequest request) throws ServiceException {
        try {
            daoHelper.startTransaction();
            User user = signUpUser(request);
            daoHelper.endTransaction();
            return user;
        } catch (DaoException e) {
            try {
                daoHelper.cancelTransaction();
                throw new ServiceException(e.getMessage(), e);
            } catch (DaoException daoException) {
                throw new ServiceException(daoException.getMessage(), daoException);
            }
        }
    }

    // Visible for testing
    public String encryptPassword(String password) {
        return wrapEncryptPassword(password);
    }

    private User signUpUser(SignUpRequest request) throws DaoException, EntityAlreadyExistsException {
        UserDao dao = daoHelper.userDao(builder);
        if (dao.getByEmail(request.getEmail()).isPresent()) {
            throw new EntityAlreadyExistsException();
        }
        User user = mapToUser(request);
        dao.save(user);
        return dao.getByEmail(user.getEmail()).get();
    }

    private User mapToUser(SignUpRequest request) {
        return new User(
            false,
            request.getEmail(),
            encryptPassword(request.getPassword()),
            request.getFirstName(),
            request.getLastName()
        );
    }

    private String wrapEncryptPassword(String password) {
        return DigestUtils.md5Hex(password);
    }

}
