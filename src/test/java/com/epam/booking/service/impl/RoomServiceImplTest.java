package com.epam.booking.service.impl;

import com.epam.booking.dao.api.RoomDao;
import web.entity.room.Room;
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
public class RoomServiceImplTest {

  private RoomServiceImpl roomService;

  @Mock
  private RoomDao roomDao;

  @Before
  public void setUp() {
    roomService = new RoomServiceImpl(roomDao);
  }

  @Test
  public void setActiveById_NormalFlow_StatusSet() throws DaoException, ServiceException {
    Room room = mock(Room.class);
    when(roomDao.getById(1)).thenReturn(Optional.of(room));

    roomService.setActiveById(1, true);

    verify(room, times(1)).setActive(true);
  }

  @Test(expected = ServiceException.class)
  public void setActiveById_DatabaseIsDown_Exception() throws DaoException, ServiceException {
    when(roomDao.getById(1)).thenThrow(new DaoException());

    roomService.setActiveById(1, true);
  }

  @Test(expected = ServiceException.class)
  public void setActiveById_NotFound_Exception() throws DaoException, ServiceException {
    when(roomDao.getById(1)).thenReturn(Optional.empty());

    roomService.setActiveById(1, true);
  }

}
