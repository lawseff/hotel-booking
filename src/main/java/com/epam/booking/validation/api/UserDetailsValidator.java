package com.epam.booking.validation.api;

public interface UserDetailsValidator {

  boolean isValidEmail(String email);
  boolean isValidPassword(String password);
  boolean isValidName(String name);

}
