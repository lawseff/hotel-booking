package com.epam.booking.command.impl;

import com.epam.booking.command.CommandResult;
import com.epam.booking.exception.ServiceException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShowPageCommandTest {

  @Test
  public void execute_Page_RedirectedToPage() throws ServiceException {
    ShowPageCommand command = new ShowPageCommand("/test-page-2");

    CommandResult result = command.execute(null, null);

    assertTrue(result.isRedirect());
    assertEquals("/test-page-2", result.getPage());
  }

}
