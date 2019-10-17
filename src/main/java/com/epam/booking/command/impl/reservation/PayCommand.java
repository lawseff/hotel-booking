package com.epam.booking.command.impl.reservation;

import com.epam.booking.command.Command;
import com.epam.booking.command.CommandResult;
import com.epam.booking.entity.reservation.Reservation;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.ReservationService;
import com.epam.booking.service.api.PaymentService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PayCommand extends AbstractReservationCommand implements Command {

    private static final String RESULT_URL = "/controller?command=show_reservations_page&details_id=";

    private static final String CARD_NUMBER_PARAMETER = "card_number";
    private static final String VALID_THRU_PARAMETER = "valid_thru";
    private static final String CVV_NUMBER_PARAMETER = "cvv_number";

    private PaymentService paymentService;
    private ReservationService reservationService;

    public PayCommand(PaymentService paymentService, ReservationService reservationService) {
        super(reservationService);
        this.paymentService = paymentService;
        this.reservationService = reservationService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        checkCredentials(request);
        Reservation reservation = getReservation(request);
        int id = reservation.getId();
        String cardNumber = request.getParameter(CARD_NUMBER_PARAMETER);
        String validThru = request.getParameter(VALID_THRU_PARAMETER);
        String cvvNumber = request.getParameter(CVV_NUMBER_PARAMETER);
        if (paymentService.tryToPay(id, cardNumber, validThru, cvvNumber)) {
            reservationService.setPaid(id);
        } else {
            throw new ServiceException("Payment failed");
        }
        return CommandResult.createForwardCommandResult(RESULT_URL + id);
    }

}
