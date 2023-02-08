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

    public void validateUser(User user) throws UserException {
        if(!user.getName().matches("[a-zA-Z]+")){
            throw new UserException("Greska pri validaciji podataka!");
        }

    }

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
        try{
            DaoFactory.userDao().delete(id);
        } catch (Exception e) {
           throw new UserException("Korisnik s tim ID-om ne postoji!");
        }
    }

    /**
     * Insert into db
     * @param item to insert
     * @throws Exception ex
     */
    public void insert(User item) throws Exception {
        if(item.getId()!=0){
            throw new UserException("ID se sam popunjava!");
        }
        try{
            validateUser(item);
            DaoFactory.userDao().insert(item);
        }
        catch (Exception e){
            throw new UserException("Greska pri unosu korisnika!");
        }
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
        try {

            return DaoFactory.userDao().update(item, id);
        }
        catch (Exception e){
            throw new UserException("Greska pri izmjeni korisnika!");
        }
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
