package com.booking.service;

import com.booking.exception.ServiceException;
import java.util.List;
import org.springframework.ui.Model;
import com.booking.entity.room.RoomClass;

public interface RoomService {

    void setRooms(Model model);

    void changeRoomStatus(Integer id, boolean active);

    void savePrices(List<RoomClass> roomClass) throws ServiceException;

}
