package com.epam.booking.command.impl.reservation;

import com.epam.booking.command.Command;
import com.epam.booking.command.CommandResult;
import web.entity.reservation.Reservation;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.ReservationService;
import web.utils.CurrentPageGetter;
import web.validation.api.PaymentValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PayCommand extends AbstractReservationCommand implements Command {

    private static final String CARD_NUMBER_PARAMETER = "card_number";
    private static final String VALID_THRU_PARAMETER = "valid_thru";
    private static final String CVV_NUMBER_PARAMETER = "cvv_number";

    private ReservationService reservationService;
    private PaymentValidator validator;

    public PayCommand(ReservationService reservationService, PaymentValidator validator) {
        super(reservationService);
        this.reservationService = reservationService;
        this.validator = validator;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        checkCredentials(request);
        Reservation reservation = getReservation(request);
        int id = reservation.getId();
        String cardNumber = request.getParameter(CARD_NUMBER_PARAMETER);
        String expirationDate = request.getParameter(VALID_THRU_PARAMETER);
        String cvvNumber = request.getParameter(CVV_NUMBER_PARAMETER);
        if (!validator.isCardNumberValid(cardNumber)) {
            throw new ServiceException("Invalid card number: " + cardNumber);
        }
        if (!validator.isExpirationDateValid(expirationDate)) {
            throw new ServiceException("Invalid expiration date: " + expirationDate);
        }
        if (!validator.isCvvNumberValid(cvvNumber)) {
            throw new ServiceException("Invalid cvv number: " + cvvNumber);
        }

        // here should be actual payment process

        reservationService.setPaid(id);
        String page = CurrentPageGetter.getCurrentPage(request);
        return CommandResult.createRedirectCommandResult(page);
    }

}
