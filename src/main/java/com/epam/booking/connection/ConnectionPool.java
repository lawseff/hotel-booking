package com.epam.booking.connection;

import com.epam.booking.connection.config.DatabaseConfig;
import com.epam.booking.connection.config.DatabaseConfigFactory;
import com.epam.booking.exception.ConnectionPoolException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <P>A pool of fixed amount of connections to a database.
 * </P>
 * Pool size and max waiting time should be configured in {@link DatabaseConfig}.
 *
 * @see ProxyConnection
 * @see java.sql.Connection
 * @see DatabaseConfig
 */
public class ConnectionPool {

    private static final boolean SEMAPHORE_FAIR = true;
    private static final ConnectionPool INSTANCE = new ConnectionPool();
    private static final Lock LOCK = new ReentrantLock();

    private List<ProxyConnection> allConnections;
    private Queue<Connection> availableConnections = new LinkedList<>();
    private Semaphore semaphore;
    private long maxWaitMillis;

    private ConnectionPool() {
        DatabaseConfigFactory configFactory = new DatabaseConfigFactory();
        DatabaseConfig config = configFactory.createConfig();
        ConnectionFactory connectionFactory = new ConnectionFactory(config);

        int poolSize = config.getPoolSize();
        maxWaitMillis = config.getMaxWaitMillis();
        semaphore = new Semaphore(poolSize, SEMAPHORE_FAIR);
        allConnections = new ArrayList<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            ProxyConnection connection = connectionFactory.createProxyConnection();
            allConnections.add(connection);
            availableConnections.add(connection);
        }
    }

    /**
     * Returns the instance of {@link ConnectionPool}
     *
     * @return the {@link ConnectionPool} instance
     */
    public static ConnectionPool getInstance() {
        return INSTANCE;
    }

    /**
     * Returns a {@link Connection} object from the pool. If the pool is empty, it is waiting until
     * any connection in the pool becomes available. If connection waiting timed out, then exception is thrown.
     *
     * @return {@link Connection} object
     * @throws ConnectionPoolException if connection waiting timed out
     */
    public Connection getConnection() throws ConnectionPoolException {
        try {
            if (semaphore.tryAcquire(maxWaitMillis, TimeUnit.MILLISECONDS)) {
                LOCK.lock();
                return availableConnections.poll();
            } else {
                throw new ConnectionPoolException("Connection waiting timed out");
            }
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(e.getMessage(), e);
        } finally {
            LOCK.unlock();
        }
    }

    /**
     * Terminates all ProxyConnections in the pool.
     *
     * @throws ConnectionPoolException if exception was thrown during connection closing
     */
    public void close() throws ConnectionPoolException {
        try {
            for (ProxyConnection connection : allConnections) {
                connection.terminate();
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException(e.getMessage(), e);
        }
    }

    /**
     * Returns a ProxyConnection to the pool.
     *
     * @param connection A {@link ProxyConnection} object
     * @throws ConnectionPoolException if ProxyConnection does not belong to this {@link ConnectionPool} instance
     */
    /* package-private */ void releaseConnection(ProxyConnection connection) throws ConnectionPoolException {
        if (!allConnections.contains(connection)) {
            throw new ConnectionPoolException("Unknown connection");
        }
        availableConnections.add(connection);
        semaphore.release();
    }

}
