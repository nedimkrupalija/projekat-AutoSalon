package ba.etf.unsa.rpr.dao;


import ba.etf.unsa.rpr.domain.Reservation;
import ba.etf.unsa.rpr.exception.ReservationException;

import java.sql.Date;

/**
 * DAO interface for category
 * @author Nedim Krupalija
 */

public interface ReservationDao extends Dao<Reservation> {
    public int isReserved(int id) throws ReservationException;
    public void updateArrivalDate(Date date, int id) throws ReservationException;
}
