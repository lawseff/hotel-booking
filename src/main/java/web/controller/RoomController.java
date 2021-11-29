package web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.service.RoomService;

@Controller
@PreAuthorize("@roleService.isAdmin()")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/rooms")
    public String showRoomsPage(Model model) {
        roomService.setRooms(model);
        return "rooms";
    }


    // save_prices
    // change_room_status
    // show_rooms_page

}
