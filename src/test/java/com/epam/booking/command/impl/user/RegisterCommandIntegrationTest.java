package com.epam.booking.command.impl.user;

import com.epam.booking.builder.Builder;
import com.epam.booking.command.CommandResult;
import com.epam.booking.dao.DaoHelper;
import com.epam.booking.dao.api.UserDao;
import com.epam.booking.dao.impl.InMemoryUserDao;
import com.epam.booking.entity.User;
import com.epam.booking.exception.DaoException;
import com.epam.booking.exception.EntityAlreadyExistsException;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.UserService;
import com.epam.booking.service.impl.UserServiceImpl;
import com.epam.booking.validation.api.UserDetailsValidator;
import com.epam.booking.validation.impl.UserDetailsValidatorImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisterCommandIntegrationTest {

  private static final String EMAIL = "user@example.com";
  private static final String PASSWORD = "pass12345";
  private static final String FIRST_NAME = "John";
  private static final String LAST_NAME = "Doe";

  private RegisterCommand command;

  private UserDetailsValidator validator;

  private UserService userService;

  private UserDao userDao;

  @Mock
  private DaoHelper daoHelper;
  @Mock
  private Builder<User> builder;
  @Mock
  private HttpServletRequest request;
  @Mock
  private HttpServletResponse response;
  @Mock
  private HttpSession session;

  @Captor
  private ArgumentCaptor<User> userCaptor;

  @Before
  public void setUp() {
    mockRequestParam("email", EMAIL);
    mockRequestParam("password", PASSWORD);
    mockRequestParam("first_name", FIRST_NAME);
    mockRequestParam("last_name", LAST_NAME);
    when(request.getSession())
        .thenReturn(session);

    userDao = new InMemoryUserDao();
    validator = new UserDetailsValidatorImpl();
    userService = spy(
        new UserServiceImpl(daoHelper, builder)
    );
    doAnswer((invocation) -> invocation.getArguments()[0])
        .when((UserServiceImpl)userService).encryptPassword(any());
    when(daoHelper.userDao(builder)).thenReturn(userDao);
    command = new RegisterCommand(userService, validator);
  }

  @Test
  public void execute_ValidRequest_UserRegistered() throws ServiceException, DaoException {
    CommandResult result = command.execute(request, response);

    // then
    verify(session, times(1))
        .setAttribute(eq("user"), userCaptor.capture());
    User user = userCaptor.getValue();
    assertUser(user);
    assertTrue(result.isRedirect());
    assertEquals("/home", result.getPage());
  }

  @Test
  public void execute_ValidRequest_UserAlreadyExists() throws DaoException, ServiceException {
    // given
    User registeredUser = new User();
    registeredUser.setEmail("user@example.com");
    userDao.save(registeredUser);

    // when
    try {
      command.execute(request, response);

    // then
    } catch (EntityAlreadyExistsException e) {
      verify(session, times(1)).setAttribute("is_register_failed", true);
      /*
      * Note, that this parameter specifically indicates, that such user already exists.
      * It will be handled on the frontend and user will receive a message,
      * that this email address is already being used.
      */
    }
  }

  @Test(expected = ServiceException.class)
  public void execute_InvalidEmail_Exception() throws ServiceException {
    mockRequestParam("email", "invalid_email");

    command.execute(request, response);
  }

  @Test(expected = ServiceException.class)
  public void execute_InvalidPassword_Exception() throws ServiceException {
    mockRequestParam("password", "123");

    command.execute(request, response);
  }

  @Test(expected = ServiceException.class)
  public void execute_InvalidFirstName_Exception() throws ServiceException {
    mockRequestParam("first_name", "");

    command.execute(request, response);
  }

  @Test(expected = ServiceException.class)
  public void execute_InvalidLastName_Exception() throws ServiceException {
    mockRequestParam("last_name", "");

    command.execute(request, response);
  }

  private void mockRequestParam(String param, String value) {
    when(request.getParameter(param))
        .thenReturn(value);
  }

  private void assertUser(User user) {
    assertEquals(user.getEmail(), EMAIL);
    assertEquals(user.getPassword(), PASSWORD);
    assertEquals(user.getFirstName(), FIRST_NAME);
    assertEquals(user.getSecondName(), LAST_NAME);
  }
  
}
