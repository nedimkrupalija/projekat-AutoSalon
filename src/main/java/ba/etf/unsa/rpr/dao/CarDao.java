package ba.etf.unsa.rpr.dao;

import ba.etf.unsa.rpr.domain.Car;
import ba.etf.unsa.rpr.domain.Reservation;
import ba.etf.unsa.rpr.exception.CarException;
import ba.etf.unsa.rpr.exception.ReservationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Dao interface for cars
 * @author Nedim Krupalija
 */

public interface CarDao extends Dao<Car> {
    public ArrayList<Car> getNotReservated() throws ReservationException, CarException;
}
