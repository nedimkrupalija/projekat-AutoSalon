package ba.etf.unsa.rpr.domain;


import java.sql.Date;


/**
 * @author Nedim Krupalija
 * Reservation class that reporesent reservation in database
 * POJO specification class (setters, getters, equals, tostring, hashcode)
 */
public class Reservation {
    private int id;
    private Date reservationDate;
    private Date arrivalDate;
    private User user;
    private Car car;

}
