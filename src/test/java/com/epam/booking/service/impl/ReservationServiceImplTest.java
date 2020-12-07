package com.epam.booking.service.impl;

import com.epam.booking.dao.api.ReservationDao;
import com.epam.booking.entity.reservation.Reservation;
import com.epam.booking.entity.reservation.ReservationStatus;
import com.epam.booking.exception.DaoException;
import com.epam.booking.exception.ServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceImplTest {

  private ReservationServiceImpl reservationService;

  @Mock
  private ReservationDao reservationDao;

  @Mock
  private Reservation reservation;

  @Before
  public void setUp() throws DaoException {
    reservationService = new ReservationServiceImpl(reservationDao);
    when(reservationDao.getById(1)).thenReturn(Optional.of(reservation));
    when(reservation.getReservationStatus()).thenReturn(ReservationStatus.WAITING);
  }

  @Test
  public void cancel_NormalFlow() throws ServiceException, DaoException {
    reservationService.cancel(1);

    verify(reservation, times(1)).setReservationStatus(ReservationStatus.CANCELLED);
    verify(reservationDao, times(1)).save(reservation);
  }

  @Test(expected = ServiceException.class)
  public void cancel_NotFound() throws DaoException, ServiceException {
    when(reservationDao.getById(1)).thenReturn(Optional.empty());

    reservationService.cancel(1);
  }

  @Test(expected = ServiceException.class)
  public void cancel_InvalidStatus() throws ServiceException {
    when(reservation.getReservationStatus()).thenReturn(ReservationStatus.CHECKED_OUT);

    reservationService.cancel(1);
  }

}
