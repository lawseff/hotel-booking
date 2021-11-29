package web.service;

import com.epam.booking.exception.ServiceException;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;

public interface ReservationService {

    void loadReservations(Integer id, Model model) throws ServiceException;

}
