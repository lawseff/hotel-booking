package com.epam.booking.builder.impl;

import com.epam.booking.builder.Builder;
import web.entity.room.RoomClass;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * An implementation of {@link Builder} for creating {@link RoomClass} objects.
 *
 * @see Builder
 * @see RoomClass
 */
public class RoomClassBuilder implements Builder<RoomClass> {

    private static final String DEFAULT_ID_COLUMN = "id";
    private static final String NAME_COLUMN = "class_name";
    private static final String BASIC_RATE_COLUMN = "basic_rate";
    private static final String RATE_PER_PERSON_COLUMN = "rate_per_person";

    private String idAlias;

    public RoomClassBuilder() {
        idAlias = DEFAULT_ID_COLUMN;
    }

    /**
     * Creates an instance of {@link RoomClassBuilder} with id alias.
     * This means, that id of {@link RoomClass} will be obtained from {@link ResultSet} using this alias.
     *
     * @param idAlias alias for id column
     */
    public RoomClassBuilder(String idAlias) {
        this.idAlias = idAlias;
    }

    @Override
    public RoomClass build(ResultSet resultSet) throws SQLException {
        Integer id = (Integer) resultSet.getObject(idAlias);
        if (resultSet.wasNull()) {
            return null;
        }
        String name = resultSet.getString(NAME_COLUMN);
        BigDecimal basicRate = resultSet.getBigDecimal(BASIC_RATE_COLUMN);
        BigDecimal ratePerPerson = resultSet.getBigDecimal(RATE_PER_PERSON_COLUMN);
        return new RoomClass(id, name, basicRate, ratePerPerson);
    }

}
