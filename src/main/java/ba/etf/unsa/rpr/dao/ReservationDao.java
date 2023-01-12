package ba.etf.unsa.rpr.dao;


import ba.etf.unsa.rpr.domain.Reservation;
import ba.etf.unsa.rpr.exception.ReservationException;

import java.sql.Date;
import java.util.ArrayList;

/**
 * DAO interface for category
 * @author Nedim Krupalija
 */

public interface ReservationDao extends Dao<Reservation> {
    public ArrayList<Reservation> getUserReservations(int id) throws ReservationException;
    public void insertReservation(Reservation reservation) throws ReservationException;
    public int isReserved(int id) throws ReservationException;
    public void updateArrivalDate(Date date, int id) throws ReservationException;

}
