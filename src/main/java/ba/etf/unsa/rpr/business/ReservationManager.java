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
 * business logic layer for reservation management
 * @author Nedim Krupalija
 */


public class ReservationManager {

    /**
     * Get all reservations from db
     * @return list of reservations
     * @throws ReservationException my ex
     * @throws UserException exc
     */
    public List<Reservation> getAll() throws ReservationException, UserException {
        return DaoFactory.reservationDao().getAll();
    }

    /**
     * Delete instance from db
     * @param id of res.
     * @throws CarException exc
     * @throws UserException exc
     */
    public void delete(int id) throws CarException, UserException {
        DaoFactory.reservationDao().delete(id);
    }

    /**
     * Insert into db
     * @param item to insert
     * @throws Exception ex
     */
    public void insert(Reservation item) throws Exception {
        DaoFactory.reservationDao().insert(item);
    }

    /**
     * Get item by id
     * @param id of item
     * @return item from db
     * @throws Exception exc
     */
    public Reservation getByid(int id) throws Exception {
        return DaoFactory.reservationDao().getById(id);
    }

    /**
     * Update item in db
     * @param item to take data from
     * @param id to update
     * @return updated item
     * @throws Exception exc
     */
    public Reservation update(Reservation item, int id) throws Exception {
        return DaoFactory.reservationDao().update(item,id);
    }


}
