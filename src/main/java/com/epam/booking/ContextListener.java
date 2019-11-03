package com.epam.booking;

import com.epam.booking.connection.ConnectionPool;
import com.epam.booking.exception.ConnectionPoolException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connectionPool.close();
        } catch (ConnectionPoolException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

}
