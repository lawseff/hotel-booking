package com.epam.booking.command.impl.reservation;

import web.entity.reservation.Reservation;
import web.entity.room.Room;
import com.epam.booking.exception.ServiceException;
import com.epam.booking.service.api.ReservationService;
import com.epam.booking.service.api.RoomService;
import com.epam.booking.utils.RoomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApproveCommandTest {

  @InjectMocks
  private ApproveCommand command;

  @Mock
  private RoomService roomService;

  @Mock
  private ReservationService reservationService;

  @Mock
  private RoomUtils roomUtils;

  @Mock
  private Room room;

  @Mock
  private Reservation reservation;

  @Before
  public void setUp() {
    when(room.isActive()).thenReturn(true);
    when(roomUtils.isRoomSuitable(room, reservation)).thenReturn(true);
    when(roomUtils.isRoomFree(eq(room), any(), any(), any())).thenReturn(true);
  }

  @Test
  public void validateRoom_Valid_NoException() throws ServiceException {
    command.validateRoom(room, reservation);
  }

  @Test(expected = ServiceException.class)
  public void validateRoom_NotActive_Exception() throws ServiceException {
    when(room.isActive()).thenReturn(false);

    command.validateRoom(room, reservation);
  }

  @Test(expected = ServiceException.class)
  public void validateRoom_NotSuitable_Exception() throws ServiceException {
    when(roomUtils.isRoomSuitable(room, reservation)).thenReturn(false);

    command.validateRoom(room, reservation);
  }

  @Test(expected = ServiceException.class)
  public void validateRoom_IsNotFree_Exception() throws ServiceException {
    when(roomUtils.isRoomFree(eq(room), any(), any(), any())).thenReturn(false);

    command.validateRoom(room, reservation);
  }

}
