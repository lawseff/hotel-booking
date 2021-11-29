package web.controller;

import com.epam.booking.utils.CurrentPageGetter;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
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

    @PostMapping("/rooms/{id}/status")
    public RedirectView changeRoomStatus(
            @PathVariable("id") Integer id,
            @RequestParam(name = "checkbox", required = false) String checkbox,
            HttpServletRequest request
    ) {
        roomService.changeRoomStatus(id, isCheckboxChecked(checkbox));
        String page = CurrentPageGetter.getCurrentPage(request);
        return new RedirectView(page);
    }


    // save_prices

    private boolean isCheckboxChecked(String checkboxParameter) {
        return checkboxParameter != null;
    }

}
