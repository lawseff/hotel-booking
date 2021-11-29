package web.controller;

import com.epam.booking.exception.ServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import web.service.ReservationService;

@Controller
@PreAuthorize("@roleService.isUser()")
public class ReservationController {

    //    approve
//    check_in
//    check_out
//    pay
//    cancel_reservation
//    show_reservations_page

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @GetMapping({"/reservations", "/reservations/{id}"})
    public String showReservationsPage(
            @PathVariable(name = "id", required = false) Integer id,
            Model model
    ) throws ServiceException {
        service.loadReservations(id, model);
        return "reservations";
    }

}
