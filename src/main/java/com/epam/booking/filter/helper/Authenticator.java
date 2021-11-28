package com.epam.booking.filter.helper;

import web.entity.User;

public interface Authenticator {

    boolean hasAuthority(User user, String commandName);

}
