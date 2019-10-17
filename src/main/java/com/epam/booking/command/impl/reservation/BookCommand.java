package com.epam.booking.command.impl.reservation;

import com.epam.booking.command.Command;
import com.epam.booking.command.CommandResult;
import com.epam.booking.entity.User;
import com.epam.booking.entity.room.RoomClass;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.ReservationService;
import com.epam.booking.service.api.RoomClassService;
import com.epam.booking.validation.api.BookingDetailsValidator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class BookCommand implements Command {

    private static final CommandResult RESULT =
            CommandResult.createForwardCommandResult("/controller?command=show_reservations_page");

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final String ARRIVAL_DATE_PARAMETER = "arrival_date";
    private static final String DEPARTURE_DATE_PARAMETER = "departure_date";
    private static final String ROOM_CLASS_PARAMETER = "room_class";
    private static final String PERSONS_AMOUNT_PARAMETER = "persons_amount";
    private static final String USER_ATTRIBUTE = "user";

    private RoomClassService roomClassService;
    private ReservationService reservationService;
    private BookingDetailsValidator validator;

    public BookCommand(RoomClassService roomClassService, ReservationService reservationService,
                       BookingDetailsValidator validator) {
        this.roomClassService = roomClassService;
        this.reservationService = reservationService;
        this.validator = validator;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try {
            String arrivalDateParameter = request.getParameter(ARRIVAL_DATE_PARAMETER);
            Date arrivalDate = DATE_FORMAT.parse(arrivalDateParameter);
            String departureDateParameter = request.getParameter(DEPARTURE_DATE_PARAMETER);
            Date departureDate = DATE_FORMAT.parse(departureDateParameter);
            if (!validator.isPeriodOfStayValid(arrivalDate, departureDate)) {
                throw new ServiceException("Invalid stay period: " + arrivalDate + ", " + departureDate);
            }

            String roomClassName = request.getParameter(ROOM_CLASS_PARAMETER);
            Optional<RoomClass> roomClassOptional = roomClassService.findByName(roomClassName);
            if (!roomClassOptional.isPresent()) {
                throw new ServiceException("Invalid room class: " + roomClassName);
            }
            RoomClass roomClass = roomClassOptional.get();

            String personsAmountParameter = request.getParameter(PERSONS_AMOUNT_PARAMETER);
            int personsAmount = Integer.parseInt(personsAmountParameter);
            if (!validator.isPersonsAmountValid(personsAmount)) {
                throw new ServiceException("Invalid amount of persons: " + personsAmount);
            }

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER_ATTRIBUTE);
            reservationService.book(user, arrivalDate, departureDate, roomClass, personsAmount);
            return RESULT;
        } catch (ParseException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
