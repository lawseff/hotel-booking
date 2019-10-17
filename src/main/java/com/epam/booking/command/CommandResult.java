package com.epam.booking.command;

public class CommandResult {

    private final String page;
    private final boolean redirect;

    private CommandResult(String page, boolean redirect) {
        this.page = page;
        this.redirect = redirect;
    }

    public static CommandResult createRedirectCommandResult(String page) {
        return new CommandResult(page, true);
    }

    public static CommandResult createForwardCommandResult(String page) {
        return new CommandResult(page, false);
    }

    public String getPage() {
        return page;
    }

    public boolean isRedirect() {
        return redirect;
    }

}
