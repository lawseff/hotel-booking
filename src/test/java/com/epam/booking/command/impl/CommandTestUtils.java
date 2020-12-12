package com.epam.booking.command.impl;

import com.epam.booking.command.Command;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.when;

public class CommandTestUtils {

  public static void mockCurrentPage(HttpServletRequest request) {
    when(request.getHeader("referer"))
        .thenReturn("http://localhost:8080/hotel-booking/test-page");

    when(request.getScheme()).thenReturn("http");
    when(request.getServerName()).thenReturn("localhost");
    when(request.getServerPort()).thenReturn(8080);
    when(request.getContextPath()).thenReturn("/hotel-booking");
  }

}
