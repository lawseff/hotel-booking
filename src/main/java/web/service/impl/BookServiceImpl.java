package web.service.impl;

import com.epam.booking.exception.ServiceException;
import java.util.Date;
import java.util.Optional;
import org.springframework.stereotype.Service;
import web.entity.reservation.Reservation;
import web.entity.reservation.ReservationStatus;
import web.entity.room.RoomClass;
import web.repository.ReservationRepository;
import web.repository.RoomClassRepository;
import web.service.BookService;
import web.service.RoleService;
import web.validation.api.BookingDetailsValidator;

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
