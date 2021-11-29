package com.booking.service.impl;

import com.booking.exception.ServiceException;
import com.booking.validation.api.BookingDetailsValidator;
import java.util.Date;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.booking.entity.reservation.Reservation;
import com.booking.entity.reservation.ReservationStatus;
import com.booking.entity.room.RoomClass;
import com.booking.repository.ReservationRepository;
import com.booking.repository.RoomClassRepository;
import com.booking.service.BookService;
import com.booking.service.RoleService;

@Service
public class BookServiceImpl implements BookService {

    private final BookingDetailsValidator validator;
    private final RoomClassRepository roomClassRepository;
    private final RoleService roleService;
    private final ReservationRepository reservationRepository;

    public BookServiceImpl(BookingDetailsValidator validator, RoomClassRepository roomClassRepository, RoleService roleService, ReservationRepository reservationRepository) {
        this.validator = validator;
        this.roomClassRepository = roomClassRepository;
        this.roleService = roleService;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void book(Date arrivalDate, Date departureDate, Integer personsAmount, String roomClassName) throws ServiceException {
        boolean isValid = validator.isPeriodOfStayValid(arrivalDate, departureDate)
                && validator.isPersonsAmountValid(personsAmount);
        if (!isValid) {
            throw new ServiceException("Bad request");
        }
        Optional<RoomClass> roomClassOptional = roomClassRepository.findByName(roomClassName);
        if (!roomClassOptional.isPresent()) {
            throw new ServiceException("Invalid room class");
        }
        Reservation reservation = new Reservation(roleService.getCurrentUser(),
                roomClassOptional.get(), null,
                ReservationStatus.WAITING,
                arrivalDate, departureDate,
                personsAmount, null);
        reservationRepository.save(reservation);
    }

}
