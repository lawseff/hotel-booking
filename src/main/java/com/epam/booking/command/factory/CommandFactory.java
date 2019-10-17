package com.epam.booking.command.factory;

import com.epam.booking.command.Command;

public interface CommandFactory {

    Command createCommand(String commandName);


}
