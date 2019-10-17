package com.epam.booking.command.impl;

import com.epam.booking.command.Command;
import com.epam.booking.command.CommandResult;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.utils.CurrentPageGetter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdatePageCommand implements Command {

    public UpdatePageCommand(CurrentPageGetter currentPageGetter) {
        this.currentPageGetter = currentPageGetter;
    }

    private CurrentPageGetter currentPageGetter;

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String page = currentPageGetter.getCurrentPage(request);
        return CommandResult.createRedirectCommandResult(page);
    }

}
