package web.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import web.repository.RoomClassRepository;
import web.repository.RoomRepository;
import web.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

    private static final String ROOM_CLASSES_ATTRIBUTE = "room_classes";
    private static final String ROOMS_ATTRIBUTE = "rooms";

    private final RoomRepository roomRepository;
    private final RoomClassRepository roomClassRepository;

    public RoomServiceImpl(RoomRepository roomRepository, RoomClassRepository roomClassRepository) {
        this.roomRepository = roomRepository;
        this.roomClassRepository = roomClassRepository;
    }

    @Override
    public void setRooms(Model model) {
        model.addAttribute(ROOM_CLASSES_ATTRIBUTE, roomClassRepository.findAll());
        model.addAttribute(ROOMS_ATTRIBUTE, roomRepository.findAll());
    }

    @Override
    @Transactional
    public void changeRoomStatus(Integer id, boolean active) {
        roomRepository.findById(id)
                .ifPresent(room -> room.setActive(active));
    }

}
