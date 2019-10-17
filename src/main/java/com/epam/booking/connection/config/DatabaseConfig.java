package com.epam.booking.connection.config;

/**
 * A class, which contains database and pool configuration.
 *
 * @see com.epam.booking.connection.ConnectionPool
 * @see com.epam.booking.connection.ConnectionFactory
 */
public class DatabaseConfig {

    private final String driver;
    private final String url;
    private final String user;
    private final String password;
    private final int poolSize;
    private final long maxWaitMillis;

    public DatabaseConfig(String driver, String url, String user, String password,
                          int poolSize, long maxWaitMillis) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
        this.poolSize = poolSize;
        this.maxWaitMillis = maxWaitMillis;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public long getMaxWaitMillis() {
        return maxWaitMillis;
    }
}
