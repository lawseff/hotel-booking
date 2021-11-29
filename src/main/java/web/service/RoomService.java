package web.service;

import org.springframework.ui.Model;

public interface RoomService {

    void setRooms(Model model);

    void changeRoomStatus(Integer id, boolean active);

}
