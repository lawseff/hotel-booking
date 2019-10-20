package com.epam.booking.filter.impl;

import com.epam.booking.filter.AbstractFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@WebFilter(filterName = "LocaleFilter", urlPatterns = { "/*" })
public class LocaleFilter extends AbstractFilter {

    private static final String LOCALE_ATTRIBUTE = "locale";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpSession session = getSession(servletRequest);
        if (session.getAttribute(LOCALE_ATTRIBUTE) == null) {
            Locale locale = servletRequest.getLocale();
            session.setAttribute(LOCALE_ATTRIBUTE, locale);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
