package com.epam.booking.service.api;

import com.epam.booking.dto.SignUpRequest;
import web.entity.User;
import com.epam.booking.exception.ServiceException;
import java.util.Optional;

public interface UserService {

    Optional<User> getUserByEmailAndPassword(String email, String password) throws ServiceException;

    User signUp(SignUpRequest request) throws ServiceException;

}
