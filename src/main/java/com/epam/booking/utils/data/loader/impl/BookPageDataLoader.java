package com.epam.booking.utils.data.loader.impl;

import com.epam.booking.entity.room.RoomClass;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.RoomClassService;
import com.epam.booking.utils.data.loader.PageDataLoader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class BookPageDataLoader implements PageDataLoader {

    private static final String ROOM_CLASSES_ATTRIBUTE = "room_classes";
    private RoomClassService roomClassService;

    public BookPageDataLoader(RoomClassService roomClassService) {
        this.roomClassService = roomClassService;
    }

    @Override
    public void loadDataToSession(HttpServletRequest request) throws ServiceException {
        List<RoomClass> roomClasses = roomClassService.getAllRoomClasses();
        HttpSession session = request.getSession();
        session.setAttribute(ROOM_CLASSES_ATTRIBUTE, roomClasses);
    }

}
