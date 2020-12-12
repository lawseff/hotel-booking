package com.epam.booking.command.impl;

import com.epam.booking.command.CommandResult;
import com.epam.booking.exception.ServiceException;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class UpdatePageCommandTest {

  @Test
  public void execute_Request_SamePage() throws ServiceException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    CommandTestUtils.mockCurrentPage(request);

    CommandResult result = new UpdatePageCommand().execute(request, null);

    assertTrue(result.isRedirect());
    assertEquals("/test-page", result.getPage());
  }

}
