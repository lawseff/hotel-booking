package web.controller;

import com.epam.booking.exception.ServiceException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import web.service.ReservationService;
import web.utils.CurrentPageGetter;

@Controller
public class ReservationController {

    //    check_in
    //    check_out

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @GetMapping({"/reservations", "/reservations/{id}"})
    @PreAuthorize("@roleService.isUser()")
    public String showReservationsPage(
            @PathVariable(name = "id", required = false) Integer id,
            Model model
    ) throws ServiceException {
        service.loadReservations(id, model);
        return "reservations";
    }

    @PostMapping("/reservations/{id}/cancel")
    @PreAuthorize("@roleService.isUser()")
    public RedirectView cancelReservation(@PathVariable("id") Integer id, HttpServletRequest request) throws ServiceException {
        service.cancelReservation(id);
        return redirectToCurrentPage(request);
    }

    @PostMapping("/reservations/{id}/approve")
    @PreAuthorize("@roleService.isAdmin()")
    public RedirectView approveReservation(
            @PathVariable("id") Integer reservationId,
            @RequestParam("roomId") Integer roomId,
            HttpServletRequest request
    ) throws ServiceException {
        service.approve(reservationId, roomId);
        return redirectToCurrentPage(request);
    }

    @PostMapping("/reservations/{id}/pay")
    @PreAuthorize("@roleService.isUser()")
    public RedirectView payForReservation(
            @PathVariable("id") Integer id,
            @RequestParam("cardNumber") String cardNumber,
            @RequestParam("cvvNumber") String cvvNumber,
            @RequestParam("validThru") String validThru,
            HttpServletRequest request
    ) throws ServiceException {
        service.pay(id, cardNumber, cvvNumber, validThru);
        return redirectToCurrentPage(request);
    }

    private RedirectView redirectToCurrentPage(HttpServletRequest request) {
        String page = CurrentPageGetter.getCurrentPage(request);
        return new RedirectView(page);
    }

}
