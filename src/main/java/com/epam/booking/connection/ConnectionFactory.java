package com.epam.booking.connection;

import com.epam.booking.connection.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * <P>A class, which provides connections to a database.
 * </P>
 * Driver, url, etc. should be configured in {@link DatabaseConfig}.
 *
 * @see ProxyConnection
 * @see java.sql.Connection
 * @see DatabaseConfig
 */
public class ConnectionFactory {

    private DatabaseConfig config;

    public ConnectionFactory(DatabaseConfig config) {
        this.config = config;
        loadDriver();
    }

    /**
     * Creates and returns a {@link ProxyConnection} to a database
     * @return {@link ProxyConnection} object
     * @throws IllegalStateException if a connection to a database can not be obtained
     */
    public ProxyConnection createProxyConnection() {
        String url = config.getUrl();
        String user = config.getUser();
        String password = config.getPassword();
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            return new ProxyConnection(connection);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private void loadDriver() {
        String driver = config.getDriver();
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

}
