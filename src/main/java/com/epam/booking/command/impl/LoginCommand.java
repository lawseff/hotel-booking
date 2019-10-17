package com.epam.booking.command.impl;

import com.epam.booking.command.Command;
import com.epam.booking.command.CommandResult;
import com.epam.booking.entity.User;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {

    private static final CommandResult SUCCESS_LOGIN_RESULT =
            CommandResult.createRedirectCommandResult("/home");
    private static final CommandResult LOGIN_FAIL_RESULT =
            CommandResult.createRedirectCommandResult("/login");
    private static final String EMAIL_PARAMETER = "email";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String USER_ATTRIBUTE = "user";
    private static final String IS_LOGIN_FAILED_ATTRIBUTE = "is_login_failed";

    private UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String email = request.getParameter(EMAIL_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        Optional<User> userOptional = userService.login(email, password);
        User user;
        HttpSession session = request.getSession();
        if (userOptional.isPresent()) {
            user = userOptional.get();
            session.setAttribute(USER_ATTRIBUTE, user);
            return SUCCESS_LOGIN_RESULT;
        } else {
            session.setAttribute(IS_LOGIN_FAILED_ATTRIBUTE, true);
            return LOGIN_FAIL_RESULT;
        }

    }

}
