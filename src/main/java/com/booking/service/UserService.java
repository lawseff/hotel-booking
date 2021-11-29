package com.booking.service;

import com.booking.exception.ServiceException;
import com.booking.dto.SignUpRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface UserService {

    boolean login(HttpSession session, String email, String password) throws ServiceException;

    boolean register(SignUpRequest request, HttpSession session) throws ServiceException;

    void signOut(HttpServletRequest request);

}
