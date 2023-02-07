package ba.etf.unsa.rpr.business;

import ba.etf.unsa.rpr.dao.DaoFactory;
import ba.etf.unsa.rpr.domain.Car;
import ba.etf.unsa.rpr.domain.Reservation;
import ba.etf.unsa.rpr.exception.CarException;
import ba.etf.unsa.rpr.exception.ReservationException;
import ba.etf.unsa.rpr.exception.UserException;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 * business logic layer for car management
 * @author Nedim Krupalija
 */

public class CarManager {

    public void validateCar(Car car) throws CarException {

        if(car.getName().length()>45||
                Integer.parseInt(car.getYear())> Integer.parseInt(Year.now().toString()) ||
            car.gethP()<0){
           throw  new CarException("Greska pri validaciji podataka!");
        }

    }

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
        try{
            DaoFactory.carDao().delete(id);
        } catch (Exception e) {
            throw new CarException("Greska pri brisanju");
        }


    }

    /**
     * Insert into db
     * @param item to insert
     * @throws Exception ex
     */
    public void insert(Car item) throws Exception {
        if (item.getId() != 0) throw new CarException("ID must be autogenerated!");
        try {
            validateCar(item);
            DaoFactory.carDao().insert(item);
        } catch (Exception e) {
            throw new CarException("Error inserting car!");
        }
    }


    /**
     * Get item by id
     * @param id of item
     * @return item from db
     * @throws Exception exc
     */
    public Car getByid(int id) throws Exception {
        try{
           return DaoFactory.carDao().getById(id);
        }
        catch(Exception e){
            throw new CarException("Auto s datim ID-om ne postoji!");
        }
    }

    /**
     * Return not res. cars from db
     * @return
     * @throws CarException
     * @throws ReservationException
     */
    public ArrayList<Car> getNotReservated() throws CarException, ReservationException {
        return DaoFactory.carDao().getNotReservated();
    }

    /**
     * Update item in db
     * @param item to take data from
     * @param id to update
     * @return updated item
     * @throws Exception exc
     */
    public Car update(Car item, int id) throws Exception {
        try{
            validateCar(item);
            return DaoFactory.carDao().update(item,id);
        }
        catch (Exception e){
            throw new CarException("Vozilo ne postoji ili podaci nisu validni!");
        }

    }
}
