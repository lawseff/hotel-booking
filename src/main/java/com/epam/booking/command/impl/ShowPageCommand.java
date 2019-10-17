package com.epam.booking.command.impl;

import com.epam.booking.command.Command;
import com.epam.booking.command.CommandResult;
import com.epam.booking.utils.data.loader.PageDataLoader;
import com.epam.booking.exception.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowPageCommand implements Command {

    private CommandResult result;
    private PageDataLoader dataLoader;

    public ShowPageCommand(String page) {
        result = CommandResult.createRedirectCommandResult(page);
    }

    public ShowPageCommand(String page, PageDataLoader dataLoader) {
        this(page);
        this.dataLoader = dataLoader;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        if (dataLoader != null) {
            dataLoader.loadDataToSession(request);
        }
        return result;
    }

}
