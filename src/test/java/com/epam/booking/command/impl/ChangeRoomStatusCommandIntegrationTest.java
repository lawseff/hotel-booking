package com.epam.booking.command.impl;

import com.epam.booking.command.CommandResult;
import com.epam.booking.command.impl.room.ChangeRoomStatusCommand;
import com.epam.booking.exception.ServiceException;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(DataProviderRunner.class)
public class ChangeRoomStatusCommandIntegrationTest {

  private ChangeRoomStatusCommand command;

  private HttpServletRequest request;
  private HttpServletResponse response;

  public static Object[][] normalFlowParams() {
    return new Object[][] {
        {}
    };
  }

  public static Object[][] exceptionParams() {
    return new Object[][] {
        {}
    };
  }
  
  @Test
  public void execute() throws ServiceException {
    CommandResult result = command.execute(request, response);

    assertTrue(result.isRedirect());
    assertEquals("http://localhost:8080/hotel-booking/rooms", result.getPage());
  }


}
