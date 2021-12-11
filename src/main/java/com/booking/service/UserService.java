package com.booking.service;

import com.booking.exception.ServiceException;
import com.booking.dto.SignUpRequest;
import com.booking.profiling.time.Profiled;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface UserService {

    @Profiled
    boolean login(HttpSession session, String email, String password) throws ServiceException;

    boolean register(SignUpRequest request, HttpSession session) throws ServiceException;

    void signOut(HttpServletRequest request);

}
