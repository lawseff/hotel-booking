package com.epam.booking.filter.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LocaleFilterTest {

  private LocaleFilter filter = new LocaleFilter();

  private ServletRequest request;
  @Mock
  private ServletResponse response;
  @Mock
  private FilterChain filterChain;

  @Before
  public void setUp() {
    request = FilterTestUtils.mockFilterRequest(null);
  }

  @Test
  public void doFilter_NoLocale_LocaleIsSet() throws IOException, ServletException {
    when(request.getLocale())
        .thenReturn(Locale.US);

    filter.doFilter(request, response, filterChain);

    verify(((HttpServletRequest) request).getSession(), times(1))
        .setAttribute("locale", Locale.US);
  }

  @Test
  public void doFilter_LocaleIsPresent_NoAction() throws IOException, ServletException {
    when(((HttpServletRequest) request).getSession().getAttribute("locale"))
        .thenReturn(Locale.US);

    filter.doFilter(request, response, filterChain);

    verify(((HttpServletRequest) request).getSession(), times(0))
        .setAttribute(any(), any());
  }

}
