package com.epam.booking.command.impl;

import com.epam.booking.command.Command;
import com.epam.booking.command.CommandResult;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.utils.CurrentPageGetter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class ChangeLanguageCommand implements Command {

    private static final String LOCALE_PARAMETER = "locale";
    private static final String LOCALE_ATTRIBUTE = "locale";

    private CurrentPageGetter currentPageGetter;

    public ChangeLanguageCommand(CurrentPageGetter currentPageGetter) {
        this.currentPageGetter = currentPageGetter;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String localeParameter = request.getParameter(LOCALE_PARAMETER);
        Locale.Builder builder = new Locale.Builder();
        builder.setLanguageTag(localeParameter);
        Locale locale = builder.build();
        HttpSession session = request.getSession();
        session.setAttribute(LOCALE_ATTRIBUTE, locale);
        String page = currentPageGetter.getCurrentPage(request);
        return CommandResult.createRedirectCommandResult(page);
    }

}
