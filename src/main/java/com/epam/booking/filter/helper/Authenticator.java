package com.epam.booking.filter.helper;

import com.epam.booking.entity.User;

public interface Authenticator {

    boolean hasAuthority(User user, String commandName);

}
