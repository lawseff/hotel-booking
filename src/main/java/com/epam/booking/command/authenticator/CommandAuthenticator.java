package com.epam.booking.command.authenticator;

import com.epam.booking.entity.User;

public interface CommandAuthenticator { // TODO CommandAuthenticator or UserAuthenticator???

    boolean hasAuthority(User user, String commandName);

}
