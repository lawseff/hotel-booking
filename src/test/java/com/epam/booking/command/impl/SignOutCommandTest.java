package com.epam.booking.command.impl;

import com.epam.booking.command.CommandResult;
import com.epam.booking.exception.ServiceException;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SignOutCommandTest {

  @Test
  public void execute_Request_SignedOut() throws ServiceException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpSession session = mock(HttpSession.class);
    when(request.getSession()).thenReturn(session);
    Locale locale = Locale.US; // final class can't be mocked
    when(session.getAttribute("locale")).thenReturn(locale);

    CommandResult result = new SignOutCommand().execute(request, null);

    assertTrue(result.isRedirect());
    assertEquals("/home", result.getPage());
    verify(session).invalidate();
    verify(session).setAttribute("locale", locale);
  }

}
