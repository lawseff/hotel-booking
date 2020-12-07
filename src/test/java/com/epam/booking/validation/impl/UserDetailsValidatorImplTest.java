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

}
