package com.epam.booking.dao.impl;

import com.epam.booking.builder.Builder;
import com.epam.booking.dao.api.Dao;
import web.entity.Identifiable;
import com.epam.booking.exception.DaoException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao <T extends Identifiable> implements Dao<T> {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM %s WHERE id=?;";
    private static final String GET_ALL_QUERY = "SELECT * FROM %s;";

    private Builder<T> builder;
    private Connection connection;

    protected AbstractDao(Builder<T> builder, Connection connection) {
        this.builder = builder;
        this.connection = connection;
    }

    @Override
    public Optional<T> getById(int id) throws DaoException {
        String tableName = getTableName();
        String query = String.format(FIND_BY_ID_QUERY, tableName);
        String idParameter = Long.toString(id);
        return executeForSingleResult(query, idParameter);
    }

    @Override
    public List<T> getAll() throws DaoException {
        String tableName = getTableName();
        String query = String.format(GET_ALL_QUERY, tableName);
        return executeQuery(query);
    }

    protected abstract String getTableName();

    protected List<T> executeQuery(String query, Object... parameters) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setParameters(statement, parameters);
            ResultSet resultSet = statement.executeQuery();
            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                T entity = builder.build(resultSet);
                entities.add(entity);
            }
            return entities;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    protected Optional<T> executeForSingleResult(String query, Object... parameters) throws DaoException {
        List<T> items = executeQuery(query, parameters);
        return (items.size() == 1) ?
                Optional.of(items.get(0)) : Optional.empty();
    }

    protected void executeUpdate(String query, Object... parameters) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setParameters(statement, parameters);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    private void setParameters(PreparedStatement statement, Object... parameters) throws DaoException {
        for (int i = 0; i < parameters.length; i++) {
            int parameterIndex = i + 1;
            try {
                if (parameters[i] != null) {
                    statement.setObject(parameterIndex, parameters[i]);
                } else {
                    statement.setNull(parameterIndex, Types.NULL);
                }
            } catch (SQLException e) {
                throw new DaoException(e.getMessage(), e);
            }
        }
    }

}
