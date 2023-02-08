package ba.etf.unsa.rpr.business;

import ba.etf.unsa.rpr.dao.Dao;
import ba.etf.unsa.rpr.dao.DaoFactory;
import ba.etf.unsa.rpr.domain.Reservation;
import ba.etf.unsa.rpr.domain.User;
import ba.etf.unsa.rpr.exception.CarException;
import ba.etf.unsa.rpr.exception.ReservationException;
import ba.etf.unsa.rpr.exception.UserException;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
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
        try{
            return DaoFactory.reservationDao().getAll();
        }
        catch (Exception e) {
            throw new ReservationException("Greska pri dobavljanju rezervacija!");
        }
    }

    /**
     * Delete instance from db
     * @param id of res.
     * @throws CarException exc
     * @throws UserException exc
     */
    public void delete(int id) throws Exception {
        try{
            DaoFactory.reservationDao().delete(id);
        }catch (Exception e){
            throw new ReservationException("Greska pri brisanju rezervacije!");
        }
    }

    /**
     * Insert into db
     * @param item to insert
     * @throws Exception ex
     */
    public void insert(Reservation item) throws Exception {
        if(item.getId()!=0) throw new ReservationException("ID se ne smije navoditi!");

        try{
            DaoFactory.reservationDao().insert(item);
        }catch (Exception e){
            throw new ReservationException("Greska pri dodavanju rezervacije!");
        }
    }

    /**
     * Get item by id
     * @param id of item
     * @return item from db
     * @throws Exception exc
     */
    public Reservation getByid(int id) throws Exception {
        try{
            return DaoFactory.reservationDao().getById(id);
        }catch (Exception e){
            throw new ReservationException("Greska pri dobavljanju rezervacije!");
        }
    }

    /**
     * Update item in db
     * @param item to take data from
     * @param id to update
     * @return updated item
     * @throws Exception exc
     */
    public Reservation update(Reservation item, int id) throws Exception {

        try{
            return DaoFactory.reservationDao().update(item,id);
        }catch (Exception e){
            throw new ReservationException("Greska pri azuriranju rezervacije!");
        }
    }

    /**
     * Is car reserved
     * @param id of car
     * @return 0 or 1
     * @throws Exception exc
     */
    public int isReserved(int id) throws Exception {
        return DaoFactory.reservationDao().isReserved(id);
    }


    /**
     * Update arrival date of res.
     * @param date
     * @param id
     * @throws ReservationException
     */
    public void updateArrivalDate(Date date, int id) throws ReservationException{
        DaoFactory.reservationDao().updateArrivalDate(date,id);
    }


    /**
     * Insert reservation into db
     * @param reservation
     * @throws ReservationException
     */
    public void insertReservation(Reservation reservation) throws ReservationException{
        DaoFactory.reservationDao().insertReservation(reservation);
    }

    public ArrayList<Reservation> getUserReservations(int id) throws ReservationException{
        return DaoFactory.reservationDao().getUserReservations(id);
    }


}
