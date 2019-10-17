package com.epam.booking.command.impl.reservation;

import com.epam.booking.command.Command;
import com.epam.booking.command.CommandResult;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.ReservationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetCheckedOutCommand implements Command {

    private static final String ID_PARAMETER = "id";
    private static final String RESULT_URL = "/controller?command=show_reservations_page&details_id=";

    private ReservationService reservationService;

    public SetCheckedOutCommand(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String idParameter = request.getParameter(ID_PARAMETER);
        int id = Integer.parseInt(idParameter);
        reservationService.setCheckedOut(id);
        return CommandResult.createForwardCommandResult(RESULT_URL + id);
    }

}
