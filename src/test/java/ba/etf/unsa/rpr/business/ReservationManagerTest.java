package ba.etf.unsa.rpr.business;

import ba.etf.unsa.rpr.domain.Reservation;
import ba.etf.unsa.rpr.exception.ReservationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author Nedim Krupalija
 */
class ReservationManagerTest {

    @Test
    void getAll() {
        assertDoesNotThrow(()->{new ReservationManager().getAll();});
    }




    @Test
    void getByid() {
        assertThrows(ReservationException.class, ()->{new ReservationManager().getByid(99999);});
    }
}