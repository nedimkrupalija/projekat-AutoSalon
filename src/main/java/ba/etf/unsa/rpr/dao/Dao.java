package ba.etf.unsa.rpr.dao;

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
    T getById(int id);


    /**
     * Insert given item in database
     *
     * @param item
     */
    void insert(T item);

    /**
     * Update item based on id
     * @param item
     * @return updated item
     */
    T update(T item,int id);

    /**
     * Delete item from database based on ID
     * @param id
     */
    void delete(int id);


    /**
     * Return all items from database in a list
     * @return
     */
    List<T> getAll();




}
