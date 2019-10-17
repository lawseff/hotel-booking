package com.epam.booking.command.impl.reservation;

import com.epam.booking.command.Command;
import com.epam.booking.command.CommandResult;
import com.epam.booking.entity.reservation.Reservation;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.ReservationService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelReservationCommand extends AbstractReservationCommand implements Command {

    private static final String RESULT_URL = "/controller?command=show_reservations_page&details_id=";

    private ReservationService reservationService;

    public CancelReservationCommand(ReservationService reservationService) {
        super(reservationService);
        this.reservationService = reservationService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        checkCredentials(request);
        Reservation reservation = getReservation(request);
        int id = reservation.getId();
        reservationService.cancel(id);
        return CommandResult.createForwardCommandResult(RESULT_URL + id);
    }

}
