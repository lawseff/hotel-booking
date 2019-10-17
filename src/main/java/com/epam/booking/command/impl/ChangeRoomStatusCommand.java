package com.epam.booking.command.impl;

import com.epam.booking.command.Command;
import com.epam.booking.command.CommandResult;
import com.epam.booking.utils.CurrentPageGetter;
import com.epam.booking.utils.data.loader.PageDataLoader;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.RoomService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeRoomStatusCommand implements Command {

    private static final String CHECKBOX_NAME = "checkbox";
    private static final String ID_PARAMETER = "id";

    private RoomService roomService;
    private PageDataLoader roomsPageDataLoader;
    private CurrentPageGetter currentPageGetter;

    public ChangeRoomStatusCommand(RoomService roomService,
                                   PageDataLoader roomsPageDataLoader, CurrentPageGetter currentPageGetter) {
        this.roomService = roomService;
        this.roomsPageDataLoader = roomsPageDataLoader;
        this.currentPageGetter = currentPageGetter;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String idParameter = request.getParameter(ID_PARAMETER);
        int id = Integer.parseInt(idParameter);
        String checkboxParameter = request.getParameter(CHECKBOX_NAME);
        boolean active = isCheckboxChecked(checkboxParameter);

        roomService.setActiveById(id, active);

        roomsPageDataLoader.loadDataToSession(request);
        String page = currentPageGetter.getCurrentPage(request);
        return CommandResult.createRedirectCommandResult(page);
    }

    private boolean isCheckboxChecked(String checkboxParameter) {
        return checkboxParameter != null;
    }

}
