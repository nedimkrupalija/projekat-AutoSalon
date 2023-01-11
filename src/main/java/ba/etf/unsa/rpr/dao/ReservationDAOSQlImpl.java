package ba.etf.unsa.rpr.dao;

import ba.etf.unsa.rpr.domain.Car;
import ba.etf.unsa.rpr.domain.Reservation;
import ba.etf.unsa.rpr.domain.User;
import ba.etf.unsa.rpr.exception.CarException;
import ba.etf.unsa.rpr.exception.ReservationException;
import ba.etf.unsa.rpr.exception.UserException;

import java.io.FileReader;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ReservationDAOSQlImpl extends AbstractDao<Reservation> implements ReservationDao {


    /**
     * Default constructor for ReservationDao implementation, makes connection to database
     * Parameters hidden
     */
    public ReservationDAOSQlImpl(){
        super("Reservations");

    }

    /**
     * Method that inserts reservation into db
     * @param reservation
     * @throws ReservationException
     */
    public void insertReservation(Reservation reservation) throws ReservationException {
        String query = "INSERT INTO Reservation (reservation_date, arrival_date, user_fk, car_fk) VALUES (?,?,?,?)";
        try{
            PreparedStatement stmt = getConn().prepareStatement(query);
            stmt.setDate(1, reservation.getReservationDate());
            stmt.setDate(2, reservation.getReservationDate());
            stmt.setInt(3, reservation.getUser().getId());
            stmt.setInt(4, reservation.getCar().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ReservationException("Greska pri unosu rezervacije!");
        }
    }


    /**
     * Help method for updating date of arrival
     * @param date
     * @param id
     */
    public void updateArrivalDate(Date date, int id) throws ReservationException {
        String query = "UPDATE Reservations SET arrival_date = ? WHERE id = ?";
        try{
            PreparedStatement stmt = getConn().prepareStatement(query);
            stmt.setDate(1,date);
            stmt.setInt(2,id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ReservationException("Greska pri izmjeni datuma!");
        }
    }




    /**
     * Method that checks if car is reserved
     * @param id of car
     * @return 0/1 not reserved/reserved
     */
    public int isReserved(int id) throws ReservationException {
        String query = "SELECT COUNT(*) FROM Reservations where car_fk = ?";
        try{
            PreparedStatement stmt = getConn().prepareStatement(query);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            int res = 0;
            int count = 0;
            if(rs.next()){
                count =  rs.getInt("COUNT(*)");
            }
            rs.close();
            return count;

        } catch (SQLException e) {
            throw new ReservationException("Greska pri provjeri da li je auto rezervisano!");
        }
    }


    /**
     * ORM - row2res mapping
     * Sets parems. of reservation returned from db
     * @param rs resultset
     * @return Reservation object
     */
    @Override
    public Reservation row2object(ResultSet rs) {
        try{
            Reservation reservation = new Reservation();
            reservation.setId(rs.getInt("id"));
            reservation.setReservationDate(rs.getDate("reservation_date"));
            reservation.setArrivalDate(rs.getDate("arrival_date"));
            reservation.setUser(new UserDaoSQLImpl().getById(rs.getInt("user_fk")));
            reservation.setCar(new CarDaoSQLImpl().getById(rs.getInt("car_fk")));
            return reservation;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that translates given object into form for accessing db
     * @param object to "translate"
     * @return map - string/obj
     */
    @Override
    public Map<String, Object> object2row(Reservation object) {
        Map<String, Object> row = new TreeMap<String ,Object>();
        row.put("id",object.getId());
        row.put("reservation_date",object.getReservationDate());
        row.put("arrival_date",object.getArrivalDate());
        row.put("user_fk",object.getUser().getId());
        row.put("car_fk",object.getCar().getId());
        return row;
    }
}
