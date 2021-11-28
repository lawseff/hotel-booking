package com.epam.booking.command.impl.user;

import com.epam.booking.command.CommandResult;
import web.entity.User;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginCommandTest {

  @InjectMocks
  private LoginCommand loginCommand;

  @Mock
  private UserService userService;

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpSession session;

  @Before
  public void setUp() {
    when(request.getSession()).thenReturn(session);
  }

  @Test
  public void execute_UserFound_SavedToSession() throws ServiceException {
    User user = mock(User.class);
    when(userService.getUserByEmailAndPassword(anyString(), anyString())).thenReturn(Optional.of(user));

    CommandResult result = loginCommand.execute(request, null);

    assertEquals("/home", result.getPage());
    assertTrue(result.isRedirect());
    verify(session, times(1)).setAttribute("user", user);
  }

  @Test
  public void execute_UserNotFound_LoginFailed() throws ServiceException {
    when(userService.getUserByEmailAndPassword(anyString(), anyString())).thenReturn(Optional.empty());
    CommandResult result = loginCommand.execute(request, null);

    assertEquals("/login", result.getPage());
    assertTrue(result.isRedirect());
    verify(session, times(1)).setAttribute("is_login_failed", true);
  }

}
