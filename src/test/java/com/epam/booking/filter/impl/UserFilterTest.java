package com.epam.booking.filter.impl;

import web.entity.User;
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
public class UserFilterTest {

  private UserFilter filter = new UserFilter();

  @Mock
  private User user;

  private ServletRequest request;
  @Mock
  private ServletResponse response;
  @Mock
  private FilterChain filterChain;

  @Test
  public void doFilter_UserPresent_NextFilterCalled() throws IOException, ServletException {
    request = FilterTestUtils.mockFilterRequest(user);

    filter.doFilter(request, response, filterChain);

    verify(filterChain, times(1))
        .doFilter(request, response);
  }

  @Test(expected = ServletException.class)
  public void doFilter_NotAuthenticated_Exception() throws IOException, ServletException {
    request = FilterTestUtils.mockFilterRequest(null);

    filter.doFilter(request, response, filterChain);
  }

}
