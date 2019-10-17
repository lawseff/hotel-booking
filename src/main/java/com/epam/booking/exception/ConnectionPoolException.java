package com.epam.booking.exception;

public class ConnectionPoolException extends Exception {

    public ConnectionPoolException() {

    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

}
