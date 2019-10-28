package com.epam.booking.command.impl.room;

import com.epam.booking.command.Command;
import com.epam.booking.command.CommandResult;
import com.epam.booking.entity.room.Room;
import com.epam.booking.entity.room.RoomClass;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.RoomClassService;
import com.epam.booking.service.api.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowRoomsPageCommand implements Command {

    private static final String ROOM_CLASSES_ATTRIBUTE = "room_classes";
    private static final String ROOMS_ATTRIBUTE = "rooms";
    private static final CommandResult COMMAND_RESULT = CommandResult.createForwardCommandResult("/rooms");

    private RoomClassService roomClassService;
    private RoomService roomService;

    public ShowRoomsPageCommand(RoomClassService roomClassService, RoomService roomService) {
        this.roomClassService = roomClassService;
        this.roomService = roomService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        List<RoomClass> roomClasses = roomClassService.getAllRoomClasses();
        List<Room> rooms = roomService.getAllRooms();
        request.setAttribute(ROOM_CLASSES_ATTRIBUTE, roomClasses);
        request.setAttribute(ROOMS_ATTRIBUTE, rooms);
        return COMMAND_RESULT;
    }

}
