package com.epam.booking.builder;

import com.epam.booking.entity.Identifiable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *<P>An interface, which describes creating objects from {@link ResultSet}
 *</P>
 * Those objects must be an instances of a class, which implements {@link Identifiable}
 *
 * @param <T> a class, which implements {@link Identifiable}
 * @see ResultSet
 * @see Identifiable
 */
public interface Builder <T extends Identifiable> {

    /**
     * Creates and returns an instance of T from {@link ResultSet}.
     *
     * @param resultSet an instance of {@link ResultSet}, containing information about T
     * @return an instance of a class, which implements {@link Identifiable}
     * @throws SQLException if exception was thrown in any {@link ResultSet} method
     */
    T build(ResultSet resultSet) throws SQLException;

}
