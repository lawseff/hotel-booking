package com.epam.booking.command.impl.reservation;

import com.epam.booking.utils.DaysCalculator;
import com.epam.booking.utils.PriceCalculator;
import com.epam.booking.utils.RoomPicker;
import com.epam.booking.command.Command;
import com.epam.booking.command.CommandResult;
import com.epam.booking.entity.User;
import com.epam.booking.entity.reservation.Reservation;
import com.epam.booking.entity.reservation.ReservationStatus;
import com.epam.booking.entity.room.Room;
import com.epam.booking.entity.room.RoomClass;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.ReservationService;
import com.epam.booking.service.api.RoomService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ShowReservationsPageCommand extends AbstractReservationCommand implements Command {

    private static final String DETAILS_ID_PARAMETER = "id";
    private static final String RESERVATIONS_ATTRIBUTE = "reservations";
    private static final String RESERVATION_DETAILS_ATTRIBUTE = "reservation_details";
    private static final String SUITABLE_ROOMS_ATTRIBUTE = "rooms";
    private static final CommandResult COMMAND_RESULT = CommandResult.createForwardCommandResult("/reservations");

    private ReservationService reservationService;
    private RoomService roomService;
    private DaysCalculator daysCalculator;
    private PriceCalculator priceCalculator;
    private RoomPicker roomPicker;

    public ShowReservationsPageCommand(ReservationService reservationService, RoomService roomService,
                                       DaysCalculator daysCalculator, PriceCalculator priceCalculator,
                                       RoomPicker roomPicker) {
        super(reservationService);
        this.reservationService = reservationService;
        this.roomService = roomService;
        this.daysCalculator = daysCalculator;
        this.priceCalculator = priceCalculator;
        this.roomPicker = roomPicker;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        User user = getUser(request);
        loadReservations(request, user);

        if (shouldShowDetails(request)) {
            checkCredentials(request);
            Reservation reservation = getReservation(request);
            ReservationStatus reservationStatus = reservation.getReservationStatus();
            if (user.isAdmin() && reservationStatus == ReservationStatus.WAITING) {
                processWaitingReservation(request, reservation);
            }
            request.setAttribute(RESERVATION_DETAILS_ATTRIBUTE, reservation);
        }

        return COMMAND_RESULT;
    }

    private void loadReservations(HttpServletRequest request, User user) throws ServiceException {
        List<Reservation> reservations;
        if (user.isAdmin()) {
            reservations = reservationService.getAllReservations();
        } else {
            int id = user.getId();
            reservations = reservationService.findByUserId(id);
        }
        request.setAttribute(RESERVATIONS_ATTRIBUTE, reservations);
    }

    private boolean shouldShowDetails(HttpServletRequest request) {
        String detailsId = request.getParameter(DETAILS_ID_PARAMETER);
        return detailsId != null;
    }

    private void processWaitingReservation(HttpServletRequest request, Reservation reservation) throws ServiceException {
        Date arrivalDate = reservation.getArrivalDate();
        Date departureDate = reservation.getDepartureDate();
        int days = daysCalculator.calculateDaysBetweenDates(arrivalDate, departureDate);
        RoomClass roomClass = reservation.getRoomClass();
        int personsAmount = reservation.getPersonsAmount();
        BigDecimal totalPrice = priceCalculator.calculateTotalPrice(days, roomClass, personsAmount);
        reservation.setTotalPrice(totalPrice);

        List<Room> rooms = roomService.getAllRooms();
        List<Reservation> reservations = reservationService.getAllReservations();
        List<Room> suitableRooms = roomPicker.getSuitableRooms(rooms, reservations, reservation);
        request.setAttribute(SUITABLE_ROOMS_ATTRIBUTE, suitableRooms);
    }

}
