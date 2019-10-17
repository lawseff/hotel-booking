package com.epam.booking.command.impl;

import com.epam.booking.command.Command;
import com.epam.booking.command.CommandResult;
import com.epam.booking.exception.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class SignOutCommand implements Command {

    private static final String LOCALE_ATTRIBUTE = "locale";

    private static final CommandResult RESULT =
            CommandResult.createRedirectCommandResult("/home");

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute(LOCALE_ATTRIBUTE);
        session.invalidate();
        setLocale(request, locale);
        return RESULT;
    }

    private void setLocale(HttpServletRequest request, Locale locale) {
        HttpSession session = request.getSession();
        session.setAttribute(LOCALE_ATTRIBUTE, locale);
    }

}
