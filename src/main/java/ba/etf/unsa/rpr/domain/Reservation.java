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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
