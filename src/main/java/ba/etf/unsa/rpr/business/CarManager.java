package ba.etf.unsa.rpr.business;

import ba.etf.unsa.rpr.dao.DaoFactory;
import ba.etf.unsa.rpr.domain.Car;
import ba.etf.unsa.rpr.domain.Reservation;
import ba.etf.unsa.rpr.exception.CarException;
import ba.etf.unsa.rpr.exception.ReservationException;
import ba.etf.unsa.rpr.exception.UserException;

import java.util.List;

/**
 * business logic layer for car management
 * @author Nedim Krupalija
 */

public class CarManager {


    /**
     * Get all reservations from db
     * @return list of reservations
     * @throws ReservationException my ex
     * @throws UserException exc
     */
    public List<Car> getAll() throws Exception {
        return DaoFactory.carDao().getAll();
    }

    /**
     * Delete instance from db
     * @param id of res.
     * @throws CarException exc
     * @throws UserException exc
     */
    public void delete(int id) throws Exception {
        DaoFactory.carDao().delete(id);
    }

    /**
     * Insert into db
     * @param item to insert
     * @throws Exception ex
     */
    public void insert(Car item) throws Exception {
        DaoFactory.carDao().insert(item);
    }

    /**
     * Get item by id
     * @param id of item
     * @return item from db
     * @throws Exception exc
     */
    public Car getByid(int id) throws Exception {
        return DaoFactory.carDao().getById(id);
    }

    /**
     * Update item in db
     * @param item to take data from
     * @param id to update
     * @return updated item
     * @throws Exception exc
     */
    public Car update(Car item, int id) throws Exception {
        return DaoFactory.carDao().update(item,id);
    }
}
