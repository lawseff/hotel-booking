package com.epam.booking.validation.impl;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(DataProviderRunner.class)
public class UserDetailsValidatorImplTest {

  private UserDetailsValidatorImpl validator = new UserDetailsValidatorImpl();

  @DataProvider
  public static Object[][] validEmailDataProvider() {
    return new Object[][] {
        { "A@b.c" },
        { "User_123@example.com" },
        { "complex_domain@lorem.ipsum.com" },
    };
  }

  @DataProvider
  public static Object[][] invalidEmailDataProvider() {
    return new Object[][] {
        { null },
        { "не_латинские_символы@майл.ру" },
        { "invalid_domain@abc" },
        { "too_long_email_1234@example.com" }, // 31 symbols
    };
  }

  @Test
  @UseDataProvider("validEmailDataProvider")
  public void isValidEmail_ValidEmail_True(String email) {
    // given

    // when
    boolean valid = validator.isValidEmail(email);

    // then
    assertTrue(valid);
  }

  @Test
  @UseDataProvider("invalidEmailDataProvider")
  public void isValidEmail_InvalidEmail_False(String email) {
    // given

    // when
    boolean valid = validator.isValidEmail(email);

    // then
    assertFalse(valid);
  }


  @DataProvider
  public static Object[][] isValidPassword_Invalid_False() {
    return new Object[][] {
        { null },
        {"1234"}, // too short
        { "123456789_123456789_123456789_1"} // too long
    };
  }
  @Test
  @UseDataProvider
  public void isValidPassword_Invalid_False(String password) {
    assertFalse(validator.isValidPassword(password));
  }

  @Test
  public void isValidPassword_Valid_True() {
    assertTrue(validator.isValidPassword("12345"));
  }

  @DataProvider
  public static Object[][] isValidName_Invalid_False() {
    return new Object[][] {
        { null },
        {"ab"}, // too short
        { "adbcdefghijklmnopqrstuvwxuzabcd"} // too long
    };
  }
  @Test
  @UseDataProvider
  public void isValidName_Invalid_False(String password) {
    assertFalse(validator.isValidName(password));
  }

  @Test
  public void isValidName_Valid_True() {
    assertTrue(validator.isValidName("John"));
  }

}
