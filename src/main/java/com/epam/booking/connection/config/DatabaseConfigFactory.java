package com.epam.booking.connection.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <P>A class, for creating a {@link DatabaseConfig}.
 * </P>
 * All configuration should be in database.properties resource file.
 *
 * @see DatabaseConfig
 */
public class DatabaseConfigFactory {

    private static final Logger LOGGER = LogManager.getLogger(DatabaseConfig.class);
    private static final String PROPERTIES_FILE = "database.properties";
    private static final String DRIVER = "driver";
    private static final String URL = "url";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String POOL_SIZE = "pool_size";
    private static final String MAX_WAIT_MILLIS = "max_wait_millis";

    /**
     * Creates a {@link DatabaseConfig} object from a database.properties resource file.
     *
     * @return a {@link DatabaseConfig} object.
     */
    public DatabaseConfig createConfig() {
        Properties properties = readPropertiesFromResources();
        String driverName = properties.getProperty(DRIVER);
        String url = properties.getProperty(URL);
        String user = properties.getProperty(USER);
        String password = properties.getProperty(PASSWORD);
        String poolSizeProperty = properties.getProperty(POOL_SIZE);
        int poolSize = Integer.parseInt(poolSizeProperty);
        String connectionTimeoutMillisProperty = properties.getProperty(MAX_WAIT_MILLIS);
        long connectionTimeoutMillis = Long.parseLong(connectionTimeoutMillisProperty);
        return new DatabaseConfig(driverName, url, user, password, poolSize, connectionTimeoutMillis);
    }

    private Properties readPropertiesFromResources() {
        ClassLoader loader = getClass().getClassLoader();
        try (InputStream inputStream = loader.getResourceAsStream(PROPERTIES_FILE)) {
            if (inputStream == null) {
                String message = "database.properties not found";
                LOGGER.error(message);
                throw new IllegalStateException(message);
            }
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

}
