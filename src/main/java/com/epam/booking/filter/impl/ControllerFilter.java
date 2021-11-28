package com.epam.booking.filter.impl;

import com.epam.booking.Controller;
import com.epam.booking.filter.helper.Authenticator;
import com.epam.booking.filter.helper.AuthenticatorImpl;
import web.entity.User;
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

    private Authenticator authenticator;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        authenticator = new AuthenticatorImpl();
    }

    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        User user = getUser(servletRequest);
        String commandName = servletRequest.getParameter(Controller.COMMAND_PARAMETER);
        if (authenticator.hasAuthority(user, commandName)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            throw new ServletException("User or guest has no permission to command: " + commandName);
        }

    }

}
