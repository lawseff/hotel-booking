package com.epam.booking.filter.impl;

import com.epam.booking.entity.User;
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
public class AdminFilterTest {

  private AdminFilter filter = new AdminFilter();

  @Mock
  private User user;

  private ServletRequest request;
  @Mock
  private ServletResponse response;
  @Mock
  private FilterChain filterChain;

  @Before
  public void setUp() throws ServletException {
    request = FilterTestUtils.mockFilterRequest(user);
  }

  @Test
  public void doFilter_AdminUser_NextFilterCalled() throws IOException, ServletException {
    when(user.isAdmin()).thenReturn(true);

    filter.doFilter(request, response, filterChain);

    verify(filterChain, times(1))
        .doFilter(request, response);
  }

  @Test(expected = ServletException.class)
  public void doFilter_NotAuthenticatedUser_Exception() throws IOException, ServletException {
    request = FilterTestUtils.mockFilterRequest(null);

    filter.doFilter(request, response, filterChain);
  }

  @Test(expected = ServletException.class)
  public void doFilter_NotAdminUser_Exception() throws IOException, ServletException {
    filter.doFilter(request, response, filterChain);
  }

}
