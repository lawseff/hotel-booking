package com.epam.booking.filter.impl;

import com.epam.booking.entity.User;
import com.epam.booking.filter.helper.AuthenticatorImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ControllerFilterIntegrationTest {

  private ControllerFilter filter = new ControllerFilter();

  private User user;

  private ServletRequest request;
  @Mock
  private ServletResponse response;
  @Mock
  private FilterChain filterChain;

  @Before
  public void setUp() {
    request = FilterTestUtils.mockFilterRequest(user);

    user = new User();
    filter.setAuthenticator(new AuthenticatorImpl());
  }

  @Test
  public void doFilter_Authorized_NextFilterCalled() throws IOException, ServletException {
    when(request.getParameter("command"))
        .thenReturn("change_language");

    filter.doFilter(request, response, filterChain);

    verify(filterChain, times(1)).doFilter(request, response);
  }

  @Test(expected = ServletException.class)
  public void doFilter_NotAuthorized_Exception() throws IOException, ServletException {
    when(request.getParameter("command"))
        .thenReturn("save_prices");

    filter.doFilter(request, response, filterChain);
  }

}
