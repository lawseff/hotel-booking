package web.controller;

import web.dto.SignUpRequest;
import com.epam.booking.exception.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import web.service.UserService;

@Controller
public class AuthorizationController {

    private static final String LOGIN_SUCCESS_PAGE = "/home";
    private static final String LOGIN_FAIL_PAGE = "/login";
    private static final String REGISTER_SUCCESS_PAGE = "/home";
    private static final String REGISTER_FAIL_PAGE = "/register";

    private final UserService userService;

    public AuthorizationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public RedirectView login(
            HttpSession session,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) throws ServiceException {
        boolean success = userService.login(session, email, password);
        String page = success ? LOGIN_SUCCESS_PAGE : LOGIN_FAIL_PAGE;
        return new RedirectView(page);
    }

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register";
    }

    @PostMapping("/register")
    public RedirectView register(SignUpRequest request, HttpSession session) throws ServiceException {
        boolean success = userService.register(request, session);
        String page = success ? REGISTER_SUCCESS_PAGE : REGISTER_FAIL_PAGE;
        return new RedirectView(page);
    }

    @GetMapping("/sign_out")
    public RedirectView signOut(HttpServletRequest request) {
        userService.signOut(request);
        return new RedirectView("/home");
    }

}
