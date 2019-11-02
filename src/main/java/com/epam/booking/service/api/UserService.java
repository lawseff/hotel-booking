package com.epam.booking.service.api;

import com.epam.booking.entity.User;
import com.epam.booking.exception.ServiceException;
import java.util.Optional;

public interface UserService {

    Optional<User> getUserByEmailAndPassword(String email, String password) throws ServiceException;

}
