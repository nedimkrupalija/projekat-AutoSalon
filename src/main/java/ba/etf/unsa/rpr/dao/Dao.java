package ba.etf.unsa.rpr.dao;

import ba.etf.unsa.rpr.exception.CarException;
import ba.etf.unsa.rpr.exception.ReservationException;
import ba.etf.unsa.rpr.exception.UserException;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Nedim Krupalija
 * Interface for all DAO classes
 */

public interface Dao<T> {
    /**
     * Get item from database based on ID
     * @param id
     * @return
     */
    T getById(int id) throws CarException, UserException, ReservationException;


    /**
     * Insert given item in database
     *
     * @param item
     */
    void insert(T item) throws SQLException, CarException, UserException, ReservationException;

    /**
     * Update item based on id
     * @param item
     * @return updated item
     */
    T update(T item,int id) throws SQLException, CarException, UserException, ReservationException;

    /**
     * Delete item from database based on ID
     * @param id
     */
    void delete(int id) throws UserException, CarException;


    /**
     * Return all items from database in a list
     * @return
     */
    List<T> getAll() throws UserException, ReservationException;




}
