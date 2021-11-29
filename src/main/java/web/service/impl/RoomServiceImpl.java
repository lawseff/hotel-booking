package web.service.impl;

import java.util.ArrayList;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import web.entity.room.RoomClass;
import web.repository.RoomRepository;
import web.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

    private static final String ROOM_CLASSES_ATTRIBUTE = "room_classes";
    private static final String ROOMS_ATTRIBUTE = "rooms";

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void setRooms(Model model) {
        model.addAttribute(ROOM_CLASSES_ATTRIBUTE, new ArrayList<RoomClass>());
        model.addAttribute(ROOMS_ATTRIBUTE, roomRepository.findAll());
    }

}
