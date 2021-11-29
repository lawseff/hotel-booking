package com.booking.validation;

public interface UserDetailsValidator {

  boolean isValidEmail(String email);
  boolean isValidPassword(String password);
  boolean isValidName(String name);

}
