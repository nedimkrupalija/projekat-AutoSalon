package ba.etf.unsa.rpr.dao;

import ba.etf.unsa.rpr.domain.Car;
import ba.etf.unsa.rpr.domain.Reservation;
import ba.etf.unsa.rpr.domain.User;
import ba.etf.unsa.rpr.exception.CarException;
import ba.etf.unsa.rpr.exception.ReservationException;
import ba.etf.unsa.rpr.exception.UserException;

import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ReservationDAOSQlImpl extends AbstractDao<Reservation> implements ReservationDao {

    private Connection conn;


    /**
     * Default constructor for ReservationDao implementation, makes connection to database
     * Parameters hidden
     */
    public ReservationDAOSQlImpl(){
        super("Reservations");

    }





    /**
     * Help method for updating date of arrival
     * @param date
     * @param id
     */
    public void updateArrivalDate(Date date, int id) throws ReservationException {
        String query = "UPDATE Reservations SET arrival_date = ? WHERE id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
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
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            int res = 0;
            if(rs.next()){
                return rs.getInt("COUNT(*)");
            }
            else return 0;
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
    @Override
    public Map<String, Object> object2row(Reservation object) {
        return null;
    }
}
