package ba.etf.unsa.rpr.business;

import ba.etf.unsa.rpr.dao.Dao;
import ba.etf.unsa.rpr.dao.DaoFactory;
import ba.etf.unsa.rpr.domain.Reservation;
import ba.etf.unsa.rpr.domain.User;
import ba.etf.unsa.rpr.exception.CarException;
import ba.etf.unsa.rpr.exception.ReservationException;
import ba.etf.unsa.rpr.exception.UserException;

import java.sql.SQLException;
import java.util.List;

/**
 * business logic layer for user management
 * @author Nedim Krupalija
 */

public class UserManager {

    /**
     * Get all reservations from db
     * @return list of reservations
     * @throws ReservationException my ex
     * @throws UserException exc
     */
    public List<User> getAll() throws Exception  {
        return DaoFactory.userDao().getAll();
    }

    /**
     * Delete instance from db
     * @param id of res.
     * @throws CarException exc
     * @throws UserException exc
     */
    public void delete(int id) throws Exception {
        DaoFactory.userDao().delete(id);
    }

    /**
     * Insert into db
     * @param item to insert
     * @throws Exception ex
     */
    public void insert(User item) throws Exception {
        DaoFactory.userDao().insert(item);
    }

    /**
     * Get item by id
     * @param id of item
     * @return item from db
     * @throws Exception exc
     */
    public User getByid(int id) throws Exception {
        return DaoFactory.userDao().getById(id);
    }

    /**
     * Update item in db
     * @param item to take data from
     * @param id to update
     * @return updated item
     * @throws Exception exc
     */
    public User update(User item, int id) throws Exception {
        return DaoFactory.userDao().update(item,id);
    }

    /**
     * Get user by usr, pw
     * @param username .
     * @param password .
     * @return user
     * @throws UserException exc
     */
    public User getByUserPass(String username, String password) throws UserException {
        return DaoFactory.userDao().getByNamePass(username,password);
    }



}
