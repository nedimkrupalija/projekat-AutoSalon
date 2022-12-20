package ba.etf.unsa.rpr.dao;


import ba.etf.unsa.rpr.domain.Reservation;
import ba.etf.unsa.rpr.exception.ReservationException;

/**
 * DAO interface for category
 * @author Nedim Krupalija
 */

public interface ReservationDao extends Dao<Reservation> {
    public int isReserved(int id) throws ReservationException;
}
