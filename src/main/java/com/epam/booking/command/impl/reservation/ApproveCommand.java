package com.epam.booking.command.impl.reservation;

import com.epam.booking.command.Command;
import com.epam.booking.command.CommandResult;
import com.epam.booking.entity.reservation.Reservation;
import com.epam.booking.entity.room.Room;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.ReservationService;
import com.epam.booking.service.api.RoomService;
import com.epam.booking.utils.CurrentPageGetter;
import com.epam.booking.utils.ReservationPriceCalculator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Optional;

public class ApproveCommand extends AbstractReservationCommand implements Command {

    private static final String ROOM_ID_PARAMETER = "room_id";

    private RoomService roomService;
    private ReservationService reservationService;

    public ApproveCommand(RoomService roomService, ReservationService reservationService) {
        super(reservationService);
        this.roomService = roomService;
        this.reservationService = reservationService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        Reservation reservation = getReservation(request);
        int reservationId = reservation.getId();

        String roomIdParameter = request.getParameter(ROOM_ID_PARAMETER);
        int roomId = Integer.parseInt(roomIdParameter);
        Optional<Room> roomOptional = roomService.findById(roomId);
        if (!roomOptional.isPresent()) {
            throw new ServiceException("Room not found by id: " + roomId);
        }
        Room room = roomOptional.get();

        /*
        String priceParameter = request.getParameter(PRICE_PARAMETER);
        BigDecimal totalPrice = new BigDecimal(priceParameter);
        if (!priceValidator.isPriceValid(totalPrice)) {
            throw new ServiceException("Invalid total price: " + totalPrice);
        }
         */
        BigDecimal totalPrice = ReservationPriceCalculator.calculateReservationPrice(reservation);

        reservationService.approve(reservationId, room, totalPrice);
        String page = CurrentPageGetter.getCurrentPage(request);
        return CommandResult.createRedirectCommandResult(page);
    }

}
