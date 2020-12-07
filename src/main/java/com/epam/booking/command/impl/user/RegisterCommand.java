package com.epam.booking.command.impl.user;

import com.epam.booking.command.Command;
import com.epam.booking.command.CommandResult;
import com.epam.booking.dto.SignUpRequest;
import com.epam.booking.entity.User;
import com.epam.booking.exception.EntityAlreadyExistsException;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.UserService;
import com.epam.booking.validation.api.UserDetailsValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegisterCommand implements Command {

  private static final CommandResult SUCCESS_REGISTER_RESULT =
      CommandResult.createRedirectCommandResult("/home");
  private static final CommandResult REGISTER_FAIL_RESULT =
      CommandResult.createRedirectCommandResult("/register");
  private static final String EMAIL_PARAMETER = "email";
  private static final String PASSWORD_PARAMETER = "password";
  private static final String FIRST_NAME_PARAMETER = "first_name";
  private static final String LAST_NAME_PARAMETER = "last_name";
  private static final String USER_ATTRIBUTE = "user";
  private static final String IS_REGISTER_FAILED_ATTRIBUTE = "is_register_failed";

  private UserService userService;
  private UserDetailsValidator validator;

  public RegisterCommand(UserService userService, UserDetailsValidator validator) {
    this.userService = userService;
    this.validator = validator;
  }

  @Override
  public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
    String email = request.getParameter(EMAIL_PARAMETER);
    String password = request.getParameter(PASSWORD_PARAMETER);
    String firstName = request.getParameter(FIRST_NAME_PARAMETER);
    String lastName = request.getParameter(LAST_NAME_PARAMETER);
    if (validator.isValidEmail(email)
        && validator.isValidPassword(password)
        && validator.isValidName(firstName)
        && validator.isValidName(lastName)) {
      SignUpRequest serviceRequest = new SignUpRequest(); // TODO builder
      serviceRequest.setEmail(email);
      serviceRequest.setPassword(password);
      serviceRequest.setFirstName(firstName);
      serviceRequest.setLastName(lastName);
      HttpSession session = request.getSession();
      return tryToSignUp(serviceRequest, session);
    } else {
      throw new ServiceException("Bad request");
    }
  }

  private CommandResult tryToSignUp(SignUpRequest request, HttpSession session) throws ServiceException {
    try {
      User user = userService.signUp(request);
      session.setAttribute(USER_ATTRIBUTE, user);
      return SUCCESS_REGISTER_RESULT;
    } catch (EntityAlreadyExistsException e) {
      session.setAttribute(IS_REGISTER_FAILED_ATTRIBUTE, true);
      return REGISTER_FAIL_RESULT;
    }
  }

}
