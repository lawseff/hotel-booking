package com.booking.service.impl.reservation;

import com.booking.entity.User;
import com.booking.exception.ServiceException;
import com.booking.repository.ReservationRepository;
import com.booking.service.ReservationService;
import com.booking.validation.api.PaymentValidator;
import com.booking.repository.RoomRepository;
import com.booking.utils.ReservationPriceCalculator;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import com.booking.entity.reservation.Reservation;
import com.booking.entity.reservation.ReservationStatus;
import com.booking.entity.room.Room;
import com.booking.service.RoleService;
import com.booking.utils.RoomUtils;

@Service
public class ReservationServiceImpl implements ReservationService {

    private static final String RESERVATIONS_ATTRIBUTE = "reservations";
    private static final String RESERVATION_DETAILS_ATTRIBUTE = "reservation_details";
    private static final String SUITABLE_ROOMS_ATTRIBUTE = "rooms";

    private final RoleService roleService;
    private final ReservationRepository reservationRepository;
    private final RoomUtils roomUtils;
    private final RoomRepository roomRepository;
    private final PaymentValidator paymentValidator;
    private final ReservationServiceHelper helper;

    public ReservationServiceImpl(RoleService roleService, ReservationRepository reservationRepository, RoomUtils roomUtils, RoomRepository roomRepository, PaymentValidator paymentValidator, ReservationServiceHelper helper) {
        this.roleService = roleService;
        this.reservationRepository = reservationRepository;
        this.roomUtils = roomUtils;
        this.roomRepository = roomRepository;
        this.paymentValidator = paymentValidator;
        this.helper = helper;
    }

    @Override
    public void loadReservations(Integer id, Model model) throws ServiceException {
        User user = roleService.getCurrentUser();
        loadReservations(model, user);
        if (shouldShowDetails(id)) {
            loadDetails(model, id, user);
        }
    }

    @Override
    public void cancelReservation(Integer id) throws ServiceException {
        checkPermissions(id);
        reservationRepository.findById(id).ifPresent(reservation -> {
            reservation.setReservationStatus(ReservationStatus.CANCELLED);
            reservationRepository.save(reservation);
        });
    }

    @Override
    public void approve(Integer reservationId, Integer roomId) throws ServiceException {
        Reservation reservation = reservationRepository.getById(reservationId);
        Room room = roomRepository.getById(roomId);
        reservation.setRoom(room);
        reservation.setReservationStatus(ReservationStatus.APPROVED);
        reservation.setTotalPrice(ReservationPriceCalculator.calculateReservationPrice(reservation));
        reservationRepository.save(reservation);
    }

    @Override
    public void pay(Integer id, String cardNumber, String cvvNumber, String validThru) throws ServiceException {
        if (!paymentValidator.isCardNumberValid(cardNumber)) {
            throw new ServiceException("Invalid card number: " + cardNumber);
        }
        if (!paymentValidator.isExpirationDateValid(validThru)) {
            throw new ServiceException("Invalid expiration date: " + validThru);
        }
        if (!paymentValidator.isCvvNumberValid(cvvNumber)) {
            throw new ServiceException("Invalid cvv number: " + cvvNumber);
        }
        Reservation reservation = reservationRepository.getById(id);
        reservation.setReservationStatus(ReservationStatus.PAID);
        reservationRepository.save(reservation);
    }

    @Override
    public void checkIn(Integer id) throws ServiceException {
        setStatus(id, ReservationStatus.CHECKED_IN);
    }

    @Override
    public void checkOut(Integer id) throws ServiceException {
        setStatus(id, ReservationStatus.CHECKED_OUT);
    }

    private void setStatus(Integer id, ReservationStatus status) {
        Reservation reservation = reservationRepository.getById(id);
        reservation.setReservationStatus(status);
        reservationRepository.save(reservation);
    }

    private void loadReservations(Model model, User user) {
        List<Reservation> reservations;
        if (user.isAdmin()) {
            reservations = helper.findAllReservations();
        } else {
            int id = user.getId();
            reservations = reservationRepository.findByUserId(id);
        }
        model.addAttribute(RESERVATIONS_ATTRIBUTE, reservations);
    }

    private void loadDetails(Model model, Integer id, User user) throws ServiceException {
        checkPermissions(id);
        Reservation reservation = reservationRepository.getById(id);
        ReservationStatus reservationStatus = reservation.getReservationStatus();
        if (user.isAdmin() && reservationStatus == ReservationStatus.WAITING) {
            BigDecimal totalPrice = ReservationPriceCalculator.calculateReservationPrice(reservation);
            reservation.setTotalPrice(totalPrice);
            List<Room> rooms = roomRepository.findAll();
            List<Reservation> reservations = helper.findAllReservations();
            List<Room> suitableRooms = roomUtils.getAvailableRooms(rooms, reservations, reservation);
            model.addAttribute(SUITABLE_ROOMS_ATTRIBUTE, suitableRooms);
        }
        model.addAttribute(RESERVATION_DETAILS_ATTRIBUTE, reservation);
    }

    private boolean shouldShowDetails(Integer id) {
        return id != null;
    }

    private void checkPermissions(Integer reservationId) throws ServiceException {
        Reservation reservation = reservationRepository.getById(reservationId);
        User user = roleService.getCurrentUser();;
        if (!user.isAdmin() && !reservation.getUser().equals(user)) {
            throw new ServiceException("Not authorized");
        }
    }

}
