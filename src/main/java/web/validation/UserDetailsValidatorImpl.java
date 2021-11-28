package web.validation;

import org.springframework.stereotype.Component;

@Component
public class UserDetailsValidatorImpl implements UserDetailsValidator {

  private static final String EMAIL_REGEX = "^[a-zA-Z0-9_.]+@([a-zA-Z0-9\\-]+\\.)+[a-zA-Z0-9\\-]+$";
  private static final int EMAIL_MAX_LENGTH = 30;
  
  @Override
  public boolean isValidEmail(String email) {
    return email != null && email.length() <= EMAIL_MAX_LENGTH
        && email.matches(EMAIL_REGEX);
  }

  @Override
  public boolean isValidPassword(String password) {
    return password != null && password.length() >= 5 && password.length() <= 30;
  }

  @Override
  public boolean isValidName(String name) {
    return name != null && name.length() >= 3 && name.length() <= 30;
  }

}
