package web.service.impl;

import com.epam.booking.dto.SignUpRequest;
import com.epam.booking.exception.DaoException;
import com.epam.booking.exception.EntityAlreadyExistsException;
import com.epam.booking.exception.ServiceException;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.transaction.annotation.Transactional;
import web.entity.User;
import web.repository.UserRepository;
import web.validation.UserDetailsValidator;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import web.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private static final String USER_ATTRIBUTE = "user";
    private static final String IS_LOGIN_FAILED_ATTRIBUTE = "is_login_failed";
    private static final String IS_REGISTER_FAILED_ATTRIBUTE = "is_register_failed";
    private static final String LOCALE_ATTRIBUTE = "locale";

    private final UserDetailsValidator validator;
    private final UserRepository repository;

    public UserServiceImpl(UserDetailsValidator validator, UserRepository repository) {
        this.validator = validator;
        this.repository = repository;
    }

    @Override
    public boolean login(HttpSession session, String email, String password) throws ServiceException {
        Optional<User> userOptional = repository.findByEmailAndPassword(email, wrapEncryptPassword(password));
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            session.setAttribute(USER_ATTRIBUTE, user);
            return true;
        } else {
            session.setAttribute(IS_LOGIN_FAILED_ATTRIBUTE, true);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean register(SignUpRequest request, HttpSession session) throws ServiceException {
        validate(request);
        try {
            User user = signUpUser(request);
            session.setAttribute(USER_ATTRIBUTE, user);
            return true;
        } catch (EntityAlreadyExistsException e) {
            session.setAttribute(IS_REGISTER_FAILED_ATTRIBUTE, true);
            return false;
        }
    }

    @Override
    public void signOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute(LOCALE_ATTRIBUTE);
        session.invalidate();
        request.getSession().setAttribute(LOCALE_ATTRIBUTE, locale);
    }

    private void validate(SignUpRequest request) throws ServiceException {
        boolean isValid = validator.isValidName(request.getFirstName())
                && validator.isValidName(request.getLastName())
                && validator.isValidEmail(request.getEmail())
                && validator.isValidPassword(request.getPassword());
        if (!isValid) {
            throw new ServiceException("Bad request");
        }
    }

    private String encryptPassword(String password) {
        return wrapEncryptPassword(password);
    }

    private User signUpUser(SignUpRequest request) throws EntityAlreadyExistsException {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new EntityAlreadyExistsException();
        }
        User user = mapToUser(request);
        return repository.save(user);
    }

    private User mapToUser(SignUpRequest request) {
        return new User(
                false,
                request.getEmail().toLowerCase(),
                encryptPassword(request.getPassword()),
                request.getFirstName(),
                request.getLastName()
        );
    }

    private String wrapEncryptPassword(String password) {
        return DigestUtils.md5Hex(password);
    }

}
