package com.epam.booking.utils.data.loader.impl;

import com.epam.booking.entity.room.Room;
import com.epam.booking.entity.room.RoomClass;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.RoomClassService;
import com.epam.booking.service.api.RoomService;
import com.epam.booking.utils.data.loader.PageDataLoader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class RoomsPageDataLoader implements PageDataLoader {

    private static final String ROOM_CLASSES_ATTRIBUTE = "room_classes";
    private static final String ROOMS_ATTRIBUTE = "rooms";

    private RoomClassService roomClassService;
    private RoomService roomService;

    public RoomsPageDataLoader(RoomClassService roomClassService, RoomService roomService) {
        this.roomClassService = roomClassService;
        this.roomService = roomService;
    }

    @Override
    public void loadDataToSession(HttpServletRequest request) throws ServiceException {
        List<RoomClass> roomClasses = roomClassService.getAllRoomClasses();
        List<Room> rooms = roomService.getAllRooms();
        HttpSession session = request.getSession();
        session.setAttribute(ROOM_CLASSES_ATTRIBUTE, roomClasses);
        session.setAttribute(ROOMS_ATTRIBUTE, rooms);
    }

}
