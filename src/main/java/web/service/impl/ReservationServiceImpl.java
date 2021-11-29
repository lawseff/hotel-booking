package web.service.impl;

import com.epam.booking.exception.ServiceException;
import web.repository.RoomRepository;
import web.utils.ReservationPriceCalculator;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import web.entity.User;
import web.entity.reservation.Reservation;
import web.entity.reservation.ReservationStatus;
import web.entity.room.Room;
import web.repository.ReservationRepository;
import web.service.ReservationService;
import web.service.RoleService;
import web.utils.RoomUtils;

@Service
public class ReservationServiceImpl implements ReservationService {

    private static final String RESERVATIONS_ATTRIBUTE = "reservations";
    private static final String RESERVATION_DETAILS_ATTRIBUTE = "reservation_details";
    private static final String SUITABLE_ROOMS_ATTRIBUTE = "rooms";

    private final RoleService roleService;
    private final ReservationRepository reservationRepository;
    private final RoomUtils roomUtils;
    private final RoomRepository roomRepository;

    public ReservationServiceImpl(RoleService roleService, ReservationRepository reservationRepository, RoomUtils roomUtils, RoomRepository roomRepository) {
        this.roleService = roleService;
        this.reservationRepository = reservationRepository;
        this.roomUtils = roomUtils;
        this.roomRepository = roomRepository;
    }

    @Override
    public void loadReservations(Integer id, Model model) throws ServiceException {
        User user = roleService.getCurrentUser();
        loadReservations(model, user);
        if (shouldShowDetails(id)) {
            loadDetails(model, id, user);
        }
    }

    private void loadReservations(Model model, User user) {
        List<Reservation> reservations;
        if (user.isAdmin()) {
            reservations = reservationRepository.findAll();
        } else {
            int id = user.getId();
            reservations = reservationRepository.findByUserId(id);
        }
        model.addAttribute(RESERVATIONS_ATTRIBUTE, reservations);
    }

    private void loadDetails(Model model, Integer id, User user) throws ServiceException {
        Reservation reservation = reservationRepository.getById(id);
        if (!user.isAdmin() && !reservation.getUser().equals(user)) {
            throw new ServiceException("Not authorized");
        }
        ReservationStatus reservationStatus = reservation.getReservationStatus();
        if (user.isAdmin() && reservationStatus == ReservationStatus.WAITING) {
            BigDecimal totalPrice = ReservationPriceCalculator.calculateReservationPrice(reservation);
            reservation.setTotalPrice(totalPrice);
            List<Room> rooms = roomRepository.findAll();
            List<Reservation> reservations = reservationRepository.findAll();
            List<Room> suitableRooms = roomUtils.getAvailableRooms(rooms, reservations, reservation);
            model.addAttribute(SUITABLE_ROOMS_ATTRIBUTE, suitableRooms);
        }
        model.addAttribute(RESERVATION_DETAILS_ATTRIBUTE, reservation);
    }

    private boolean shouldShowDetails(Integer id) {
        return id != null;
    }

}
