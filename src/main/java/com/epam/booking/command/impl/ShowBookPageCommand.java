package com.epam.booking.command.impl;

import com.epam.booking.command.Command;
import com.epam.booking.command.CommandResult;
import com.epam.booking.entity.room.RoomClass;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.RoomClassService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowBookPageCommand implements Command {

    private static final String ROOM_CLASSES_ATTRIBUTE = "room_classes";
    private static final CommandResult COMMAND_RESULT = CommandResult.createForwardCommandResult("/book");

    private RoomClassService roomClassService;

    public ShowBookPageCommand(RoomClassService roomClassService) {
        this.roomClassService = roomClassService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        List<RoomClass> roomClasses = roomClassService.getAllRoomClasses();
        request.setAttribute(ROOM_CLASSES_ATTRIBUTE, roomClasses);
        return COMMAND_RESULT;
    }

}
