package com.epam.booking.dao.api;

import com.epam.booking.entity.Identifiable;
import com.epam.booking.exception.DaoException;
import java.util.List;
import java.util.Optional;

/**
 * Basic interface of all DAO.
 * It describes basic data operations with an instances of a class, which implements {@link Identifiable}.
 *
 * @param <T> a class, which implements {@link Identifiable}
 * @see Identifiable
 */
public interface Dao <T extends Identifiable> {

    Optional<T> getById(int id) throws DaoException;

    List<T> getAll() throws DaoException;

    /**
     * Saves an entity to the storage, if there is no entry with such id or id is null.
     * If the entry with such id already exists, then the information of this object is updated.
     *
     * @param entity an instance of a class, which implements {@link Identifiable}
     * @throws DaoException if the object couldn't be saved to the dao.
     */
    void save(T entity) throws DaoException;

    void deleteById(int id) throws DaoException;

}
