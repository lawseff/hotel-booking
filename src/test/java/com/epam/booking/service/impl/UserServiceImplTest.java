package com.epam.booking.service.impl;

import com.epam.booking.builder.Builder;
import com.epam.booking.dao.DaoHelper;
import com.epam.booking.dao.api.UserDao;
import web.dto.SignUpRequest;
import web.entity.User;
import com.epam.booking.exception.DaoException;
import com.epam.booking.exception.EntityAlreadyExistsException;
import com.epam.booking.exception.ServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

  private static final String EMAIL = "user@example.com";

  private UserServiceImpl service;

  // dependencies
  @Mock
  private DaoHelper daoHelper;
  @Mock
  private Builder<User> builder;
  @Mock
  private UserDao dao;

  @Mock
  private SignUpRequest request;
  @Mock
  private User user;

  @Captor
  private ArgumentCaptor<User> savedUserCaptor;

  @Before
  public void setUp() {
    service = spy(
        new UserServiceImpl(daoHelper, builder)
    );
    when(daoHelper.userDao(builder))
        .thenReturn(dao);
    when(request.getEmail())
        .thenReturn(EMAIL);
  }

  @Test
  public void signUp_NotRegisteredEmail_UserRegistered() throws DaoException, ServiceException {
    // given
    when(dao.getByEmail(EMAIL))
        .thenReturn(Optional.empty())
        .thenReturn(Optional.of(user));
    doReturn("").when(service)
        .encryptPassword(any());

    // when
    service.signUp(request);

    // then
    verify(dao, times(1))
        .save(savedUserCaptor.capture());
    assertEquals(EMAIL, savedUserCaptor.getValue().getEmail());
  }

  @Test(expected = EntityAlreadyExistsException.class)
  public void signUp_RegisteredEmail_Exception() throws DaoException, ServiceException {
    // given
    when(dao.getByEmail(EMAIL))
        .thenReturn(Optional.of(user));

    // when
    try {
      service.signUp(request);

    // then
    } finally {
      verify(dao, times(0))
          .save(any());
    }
  }

  @Test(expected = ServiceException.class)
  public void signUp_DatabaseNotAvailable_Exception() throws DaoException, ServiceException {
    when(dao.getByEmail(any()))
        .thenThrow(new DaoException());

    service.signUp(request);
  }

}
