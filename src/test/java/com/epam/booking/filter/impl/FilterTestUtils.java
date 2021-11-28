package com.epam.booking.filter.impl;

import web.entity.User;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FilterTestUtils {

  public static ServletRequest mockFilterRequest(User user) {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpSession session = mock(HttpSession.class);
    when(request.getSession())
        .thenReturn(session);
    when(session.getAttribute("user"))
        .thenReturn(user);
    return request;
  }

}
