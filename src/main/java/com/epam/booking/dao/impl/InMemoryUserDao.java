package com.epam.booking.dao.impl;

import com.epam.booking.dao.api.UserDao;
import com.epam.booking.entity.User;
import com.epam.booking.exception.DaoException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class InMemoryUserDao implements UserDao {

  private static final int FIRST_USER_ID = 1;

  private List<User> users = new ArrayList<>();

  @Override
  public Optional<User> getByEmailAndPassword(String email, String password) throws DaoException {
    return users.stream()
        .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
        .findFirst();
  }

  @Override
  public Optional<User> getByEmail(String email) throws DaoException {
    return users.stream()
        .filter(u -> u.getEmail().equals(email))
        .findFirst();
  }

  @Override
  public Optional<User> getById(int id) throws DaoException {
    return users.stream()
        .filter(u -> u.getId().equals(id))
        .findFirst();
  }

  @Override
  public List<User> getAll() throws DaoException {
    return Collections.unmodifiableList(users);
  }

  @Override
  public void save(User entity) throws DaoException {
    if (getByEmail(entity.getEmail()).isPresent()) {
      throw new DaoException("Unique email constraint violation: " + entity.getEmail());
    }
    if (entity.getId() == null) {
      entity.setId(generateId());
    } else if (getById(entity.getId()).isPresent()) {
      throw new DaoException("Unique id constraint violation: " + entity.getId());
    }
    users.add(entity);
  }

  @Override
  public void deleteById(int id) throws DaoException {
    users.removeIf(u -> u.getId().equals(id));
  }

  private int generateId() {
    return users.stream()
        .mapToInt(u -> u.getId() + 1)
        .max()
        .orElse(FIRST_USER_ID);
  }

}
