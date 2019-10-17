package com.epam.booking.filter.impl;

import com.epam.booking.entity.User;
import com.epam.booking.filter.AbstractFilter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AdminFilter", urlPatterns = { "/rooms" })
public class AdminFilter extends AbstractFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        User user = getUser(servletRequest);
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED); // TODO send error or throw exception?
        } else if (!user.isAdmin()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}

