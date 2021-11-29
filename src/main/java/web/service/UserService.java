package web.service;

import web.dto.SignUpRequest;
import com.epam.booking.exception.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface UserService {

    boolean login(HttpSession session, String email, String password) throws ServiceException;

    boolean register(SignUpRequest request, HttpSession session) throws ServiceException;

    void signOut(HttpServletRequest request);

}
