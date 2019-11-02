package com.epam.booking.command.impl.reservation;

import com.epam.booking.entity.User;
import com.epam.booking.entity.reservation.Reservation;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.ReservationService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class AbstractReservationCommand {

    private static final String USER_ATTRIBUTE = "user";
    private static final String ID_PARAMETER = "id";

    private ReservationService reservationService;

    protected AbstractReservationCommand(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    protected Reservation getReservation(HttpServletRequest request) throws ServiceException {
        String idParameter = request.getParameter(ID_PARAMETER);
        int id = Integer.parseInt(idParameter);
        return reservationService.getById(id)
                .orElseThrow(() -> new ServiceException("Invalid reservation id: " + id));
    }

    protected User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (User) session.getAttribute(USER_ATTRIBUTE);
    }

    protected void checkCredentials(HttpServletRequest request) throws ServiceException {
        User user = getUser(request);
        Reservation reservation = getReservation(request);
        User reservationUser = reservation.getUser();
        if (!user.isAdmin() && !user.equals(reservationUser)) {
            throw new ServiceException("User id: " + user.getId() +
                    " has no access to reservation id: " + reservation.getId());
        }
    }

}
