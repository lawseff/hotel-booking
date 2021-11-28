package com.epam.booking.builder.impl;

import com.epam.booking.builder.Builder;
import web.entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * An implementation of {@link Builder} for creating {@link User} objects.
 *
 * @see Builder
 * @see User
 */
public class UserBuilder implements Builder<User> {

    private static final String DEFAULT_ID_COLUMN = "id";
    private static final String IS_ADMIN_COLUMN = "is_admin";
    private static final String EMAIL_COLUMN = "email";
    private static final String PASSWORD_COLUMN = "user_password";
    private static final String FIRST_NAME_COLUMN = "first_name";
    private static final String SECOND_NAME_COLUMN = "second_name";

    private String idAlias;


    public UserBuilder() {
        idAlias = DEFAULT_ID_COLUMN;
    }

    /**
     * Creates an instance of {@link UserBuilder} with id alias.
     * This means, that id of {@link User} will be obtained from {@link ResultSet} using this alias.
     *
     * @param idAlias alias for id column
     */
    public UserBuilder(String idAlias) {
        this.idAlias = idAlias;
    }

    @Override
    public User build(ResultSet resultSet) throws SQLException {
        Integer id = (Integer) resultSet.getObject(idAlias);
        if (resultSet.wasNull()) {
            return null;
        }
        boolean admin = resultSet.getBoolean(IS_ADMIN_COLUMN);
        String email = resultSet.getString(EMAIL_COLUMN);
        String password = resultSet.getString(PASSWORD_COLUMN);
        String firstName = resultSet.getString(FIRST_NAME_COLUMN);
        String secondName = resultSet.getString(SECOND_NAME_COLUMN);
        return new User(id, admin, email, password, firstName, secondName);
    }

}
