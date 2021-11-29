package web.service;

import com.epam.booking.exception.ServiceException;
import org.springframework.ui.Model;

public interface ReservationService {

    void loadReservations(Integer id, Model model) throws ServiceException;

    void cancelReservation(Integer id) throws ServiceException;

    void approve(Integer reservationId, Integer roomId) throws ServiceException;

    void pay(Integer id, String cardNumber, String cvv, String validThru) throws ServiceException;

}
