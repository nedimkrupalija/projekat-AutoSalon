package ba.etf.unsa.rpr.domain;


import java.sql.Date;
import java.util.Objects;


/**
 * @author Nedim Krupalija
 * Reservation class that reporesent reservation in database
 * POJO specification class (setters, getters, equals, tostring, hashcode)
 */
public class Reservation  {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id && Objects.equals(reservationDate, that.reservationDate) && Objects.equals(arrivalDate, that.arrivalDate) && Objects.equals(user, that.user) && Objects.equals(car, that.car);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reservationDate, arrivalDate, user, car);
    }

    @Override
    public String toString() {
        return "id: " + id + ", vozilo: " + getCar().getName() + ", korisnik: " + getUser().getName() + ", datum: " + reservationDate.toString() + ", dolazak: " + arrivalDate.toString()+".";
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
