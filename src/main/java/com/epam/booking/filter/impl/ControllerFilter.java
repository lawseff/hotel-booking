package com.epam.booking.filter.impl;

import com.epam.booking.Controller;
import com.epam.booking.command.authenticator.CommandAuthenticator;
import com.epam.booking.command.authenticator.CommandAuthenticatorImpl;
import com.epam.booking.entity.User;
import com.epam.booking.filter.AbstractFilter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "ControllerFilter", urlPatterns = { "/controller" })
public class ControllerFilter extends AbstractFilter {

    private CommandAuthenticator commandAuthenticator;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        commandAuthenticator = new CommandAuthenticatorImpl();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        User user = getUser(servletRequest);
        String commandName = servletRequest.getParameter(Controller.COMMAND_PARAMETER);
        if (commandAuthenticator.hasAuthority(user, commandName)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            throw new ServletException("User or guest has no permission to command: " + commandName);
            // TODO to use logger in filter ???
        }

    }

}
