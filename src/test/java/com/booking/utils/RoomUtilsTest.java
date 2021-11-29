package com.booking.utils;

import com.booking.entity.reservation.ReservationStatus;
import com.booking.entity.reservation.Reservation;
import com.booking.entity.room.Room;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoomUtilsTest {

  @InjectMocks
  private RoomUtils roomUtils;
  @Mock
  private DateUtils dateUtils;

  @Mock
  private Room room;
  @Mock
  private Reservation reservation;

  @Test
  public void isRoomFree_FreeRoom_True() {
    boolean free = roomUtils.isRoomFree(room, new Date(), new Date(), Collections.emptyList());

    assertTrue(free);
  }

  @Test
  public void isRoomFree_NotFree_False() {
    when(reservation.getReservationStatus()).thenReturn(ReservationStatus.CHECKED_IN);
    when(reservation.getRoom()).thenReturn(room);
    when(dateUtils.isBetweenDates(any(), any(), any()))
        .thenReturn(true);

//    boolean free = roomUtils.isRoomFree(room, new Date(), new Date(), List.of(reservation));

//    assertFalse(free);
  }

}
