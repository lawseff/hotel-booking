package com.epam.booking.command.impl.reservation;

import web.entity.reservation.Reservation;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.ReservationService;
import web.validation.api.PaymentValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PayCommandTest {

  private PayCommand payCommand;

  @Mock
  private ReservationService reservationService;

  @Mock
  private PaymentValidator validator;

  @Mock
  private HttpServletRequest request;

  @Mock
  private Reservation reservation;

  @Before
  public void setUp() throws ServiceException {
    payCommand = Mockito.spy(new PayCommand(reservationService, validator));
    doNothing().when(payCommand).checkCredentials(any());
    doReturn(reservation).when(payCommand).getReservation(request);

    when(request.getHeader(any())).thenReturn("header");

    when(validator.isCardNumberValid(any())).thenReturn(true);
    when(validator.isExpirationDateValid(any())).thenReturn(true);
    when(validator.isCvvNumberValid(any())).thenReturn(true);
  }

  @Test
  public void execute_NormalFlow_Paid() throws ServiceException {
    payCommand.execute(request, null);

    verify(reservationService, times(1)).setPaid(0);
  }

  @Test(expected = ServiceException.class)
  public void execute_InvalidCardNumber_Exception() throws ServiceException {
    when(validator.isCardNumberValid(any())).thenReturn(false);

    payCommand.execute(request, null);
  }

  @Test(expected = ServiceException.class)
  public void execute_InvalidExpirationDate_Exception() throws ServiceException {
    when(validator.isExpirationDateValid(any())).thenReturn(false);

    payCommand.execute(request, null);
  }

  @Test(expected = ServiceException.class)
  public void execute_InvalidCvvNumber_Exception() throws ServiceException {
    when(validator.isCvvNumberValid(any())).thenReturn(false);

    payCommand.execute(request, null);
  }

}
