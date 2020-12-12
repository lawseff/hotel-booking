package com.epam.booking.connection.config;

import org.junit.Assert;
import org.junit.Test;

public class DatabaseConfigFactoryTest {

    private static final String EXPECTED_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String EXPECTED_URL = "jdbc:mysql://localhost:3306/hotel_booking_test";
    private static final String EXPECTED_USER = "root";
    private static final String EXPECTED_PASSWORD = "root";
    private static final int EXPECTED_POOL_SIZE = 1;
    private static final long EXPECTED_MAX_WAIT_MILLIS = 5000;

    private DatabaseConfigFactory configFactory = new DatabaseConfigFactory();

    @Test(expected = IllegalStateException.class)
    public void createConfig_MissingConfigFile_Exception() {
        configFactory.createConfig("this file does not exist");
    }

    @Test
    public void testCreateConfigShouldReturnConfigWhenMethodInvoked() {
        // given

        // when
        DatabaseConfig config = configFactory.createConfig();
        String driver = config.getDriver();
        String url = config.getUrl();
        String user = config.getUser();
        String password = config.getPassword();
        int poolSize = config.getPoolSize();
        long maxWaitMillis = config.getMaxWaitMillis();

        // then
        Assert.assertEquals(EXPECTED_DRIVER, driver);
        Assert.assertEquals(EXPECTED_URL, url);
        Assert.assertEquals(EXPECTED_USER, user);
        Assert.assertEquals(EXPECTED_PASSWORD, password);
        Assert.assertEquals(EXPECTED_POOL_SIZE, poolSize);
        Assert.assertEquals(EXPECTED_MAX_WAIT_MILLIS, maxWaitMillis);
    }

}
