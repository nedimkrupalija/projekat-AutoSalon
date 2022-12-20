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
import java.util.Properties;

public class ReservationDAOSQlImpl implements ReservationDao {

    private Connection conn ;

    /**
     * Default constructor for ReservationDao implementation, makes connection to database
     * Parameters hidden
     */
    public ReservationDAOSQlImpl(){
        try {
            FileReader reader = new FileReader("db.properties");
            Properties property = new Properties();
            property.load(reader);
            this.conn = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/"+property.getProperty("username"),property.getProperty("username"),property.getProperty("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param id to look for
     * @return Specific reservation seached by id
     */
    @Override
    public Reservation getById(int id) throws ReservationException {
        String query = "SELECT * FROM Reservations where id = ?";
        try{
            PreparedStatement stmt  = this.conn.prepareStatement(query);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Reservation reservation = new Reservation();
                reservation.setId(rs.getInt("id"));
                reservation.setReservationDate(rs.getDate("reservation_date"));
                reservation.setArrivalDate(rs.getDate("arrival_date"));
                reservation.setUser(new UserDaoSQLImpl().getById(rs.getInt("user_fk")));
                reservation.setCar(new CarDaoSQLImpl().getById(rs.getInt("car_fk")));
                rs.close();
                return reservation;
            }
            else return null;
        } catch (Exception e) {
            throw new ReservationException("Greska pri dohvacanju rezervacije!");
        }
    }


    /**
     * Insert reservation item in database
     * @param item
     */
    @Override
    public void insert(Reservation item) throws SQLException, ReservationException {
        String query = "INSERT INTO Reservations (reservation_date, arrival_date, user_fk, car_fk) values (?, ?, ?, ?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setDate(1,item.getReservationDate());
            stmt.setDate(2,item.getArrivalDate());
            stmt.setInt(3,item.getUser().getId());
            stmt.setInt(4,item.getCar().getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new ReservationException("Greska pri ubacivanju rezervacije!");
        }

    }

    /**
     * Updates specific reservation item
     * @param item from which attributes are taken
     * @param id of item to change
     * @return  updated item
     */
    @Override
    public Reservation update(Reservation item, int id) throws SQLException, ReservationException {
        String query = "UPDATE Reservations SET reservation_date = ?, arrival_date = ?, user_fk = ?, car_fk = ? WHERE id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setDate(1,item.getReservationDate());
            stmt.setDate(2,item.getArrivalDate());
            stmt.setInt(3,item.getUser().getId());
            stmt.setInt(4,item.getCar().getId());
            stmt.executeUpdate();
            return getById(id);
        } catch (Exception e) {
            throw new ReservationException("Greska pri updateu rezervacije!");
        }
    }


    /**
     * Delete item from database based on id
     * @param id
     */
    @Override
    public void delete(int id) {
        String query = "DELETE FROM Reservations WHERE id = ?";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setInt(1,id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
            if(rs.next()){
                return 1;
            }
            else return 0;
        } catch (SQLException e) {
            throw new ReservationException("Greska pri provjeri da li je auto rezervisano!");
        }
    }


    /**
     *
     * @return list of all reservations from database
     */
    @Override
    public List<Reservation> getAll() throws ReservationException {
        String query = "SELECT * FROM Reservations";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            ArrayList<Reservation> reservations = new ArrayList<Reservation>();
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(rs.getInt("id"));
                reservation.setReservationDate(rs.getDate("reservation_date"));
                reservation.setArrivalDate(rs.getDate("arrival_date"));
                reservation.setUser(new UserDaoSQLImpl().getById(rs.getInt("user_fk")));
                reservation.setCar(new CarDaoSQLImpl().getById(rs.getInt("car_fk")));
                reservations.add(reservation);
            }
            rs.close();
            return reservations;
        } catch (Exception e) {
            throw new ReservationException("Greska pri dohvacanju rezervacija!");
        }
    }


}
