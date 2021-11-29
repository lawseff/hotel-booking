package web.controller;

import com.epam.booking.exception.ServiceException;
import com.epam.booking.utils.CurrentPageGetter;
import web.validation.api.PriceValidator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import web.entity.room.RoomClass;
import web.service.RoomService;

@Controller
@PreAuthorize("@roleService.isAdmin()")
public class RoomController {

    private static final String ROOMS_VIEW = "/rooms";

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/rooms")
    public String showRoomsPage(Model model) {
        roomService.setRooms(model);
        return ROOMS_VIEW;
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

    @PostMapping("/rooms/prices")
    public RedirectView savePrices(
            @RequestParam("name") List<String> names,
            @RequestParam("basicRate") List<BigDecimal> basicRates,
            @RequestParam("ratePerPerson") List<BigDecimal> ratePerPerson
    ) throws ServiceException {
        List<RoomClass> roomClasses = map(names, basicRates, ratePerPerson);
        roomService.savePrices(roomClasses);
        return new RedirectView(ROOMS_VIEW);
    }

    private boolean isCheckboxChecked(String checkboxParameter) {
        return checkboxParameter != null;
    }

    private List<RoomClass> map(final List<String> names, List<BigDecimal> basicRates, List<BigDecimal> ratePerPerson) throws ServiceException {
        if (names.size() != basicRates.size() || basicRates.size() != ratePerPerson.size()) {
            throw new ServiceException("Bad request");
        }
        List<RoomClass> roomClasses = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            RoomClass roomClass = new RoomClass();
            roomClass.setName(names.get(i));
            roomClass.setBasicRate(basicRates.get(i));
            roomClass.setRatePerPerson(ratePerPerson.get(i));
            roomClasses.add(roomClass);
        }
        return roomClasses;
    }

}
