package com.epam.booking.command.impl;

import com.epam.booking.command.CommandResult;
import com.epam.booking.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChangeLanguageCommandTest {

  private ChangeLanguageCommand command = new ChangeLanguageCommand();

  @Mock
  private HttpServletRequest request;
  @Mock
  private HttpSession session;
  @Mock
  private HttpServletResponse response;

  @Test
  public void execute_LocaleName_LocaleIsSet() throws ServiceException {
    CommandTestUtils.mockCurrentPage(request);
    when(request.getParameter("locale"))
        .thenReturn("en-US");
    when(request.getSession())
        .thenReturn(session);

    CommandResult result = command.execute(request, response);

    verify(session).setAttribute(eq("locale"), eq(Locale.US));
    assertTrue(result.isRedirect());
    assertEquals("/test-page", result.getPage());
  }

}
