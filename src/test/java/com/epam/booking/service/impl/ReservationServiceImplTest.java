package com.epam.booking.service.impl;

import com.epam.booking.dao.api.ReservationDao;
import web.entity.reservation.Reservation;
import web.entity.reservation.ReservationStatus;
import web.entity.room.Room;
import com.epam.booking.exception.DaoException;
import com.epam.booking.exception.ServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
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
  public void cancel_NormalFlow_Cancelled() throws ServiceException, DaoException {
    reservationService.cancel(1);

    verify(reservation, times(1)).setReservationStatus(ReservationStatus.CANCELLED);
    verify(reservationDao, times(1)).save(reservation);
  }

  @Test(expected = ServiceException.class)
  public void cancel_DatabaseIsDown_Exception() throws ServiceException, DaoException {
    doThrow(new DaoException()).when(reservationDao).save(any());

    reservationService.cancel(1);
  }

  @Test(expected = ServiceException.class)
  public void cancel_NotFound_Exception() throws DaoException, ServiceException {
    when(reservationDao.getById(1)).thenReturn(Optional.empty());

    reservationService.cancel(1);
  }

  @Test(expected = ServiceException.class)
  public void cancel_InvalidStatus_Exception() throws ServiceException {
    when(reservation.getReservationStatus()).thenReturn(ReservationStatus.CHECKED_OUT);

    reservationService.cancel(1);
  }

  @Test
  public void approve_ValidStatus_Approved() throws ServiceException {
    Room room = mock(Room.class);
    when(room.getId()).thenReturn(123);
    when(reservation.getReservationStatus()).thenReturn(ReservationStatus.WAITING);

    reservationService.approve(1, room, BigDecimal.TEN);

    verify(reservation).setReservationStatus(ReservationStatus.APPROVED);
    verify(reservation).setTotalPrice(BigDecimal.TEN);
    verify(reservation).setRoom(room);
  }

  @Test(expected = ServiceException.class)
  public void approve_InvalidStatus_Exception() throws ServiceException {
    when(reservation.getReservationStatus()).thenReturn(ReservationStatus.CHECKED_OUT);

    reservationService.cancel(1);
  }

}
