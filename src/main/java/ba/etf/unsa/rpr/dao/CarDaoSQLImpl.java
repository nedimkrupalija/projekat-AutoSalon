package ba.etf.unsa.rpr.dao;

import ba.etf.unsa.rpr.domain.Car;
import ba.etf.unsa.rpr.exception.CarException;

import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * CarDao interface SQL implementation
 * @author Nedim Krupalija
 */


public class CarDaoSQLImpl implements CarDao {
    private Connection conn;

    /**
     * Constructor for CarDao implementation, makes DB connection, username and password hidden
     */
    public CarDaoSQLImpl()
    {
        try{
            FileReader reader = new FileReader("db.properties");
            Properties property = new Properties();
            property.load(reader);
            this.conn = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/"+property.getProperty("username"),property.getProperty("username"),property.getProperty("password"));
        } catch (Exception  e) {
            e.printStackTrace();
        }
    }



    /**
     * Method that returns all information about a car from DB
     * based on ID
     * @param id of car
     * @return
     */
    @Override
    public Car getById(int id) throws CarException {
        String query = "SELECT * FROM Cars where id = ?";
        try{
            PreparedStatement stmt  = this.conn.prepareStatement(query);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Car car = new Car();
                car.setId(rs.getInt("id"));
                car.setName(rs.getString("name"));
                car.setYear(rs.getString("year"));
                car.setColor(rs.getString("color"));
                car.sethP(rs.getInt("hp"));
                car.setDescription(rs.getString("description"));
                rs.close();
                return car;
            }
            else return null;
        } catch (SQLException e) {
            throw new CarException("Greska pri dohvacanju auta!");
        }
    }


    /**
     * Method that inserts given car in database
     * @param item for insertion in database
     */
    @Override
    public void insert(Car item) throws CarException {
        String query = "INSERT INTO Cars (name, year, color, hp, description) values (?, ?, ?, ?, ?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1,item.getName());
            stmt.setString(2,item.getYear());
            stmt.setString(3,item.getColor());
            stmt.setInt(4,item.gethP());
            stmt.setString(5,item.getDescription());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new CarException("Greska pri upisivanju auta!");
        }
    }

    /**
     *
     * @param item
     * @return updated car
     */

    @Override
    public Car update(Car item, int id) throws CarException {
        String query = "UPDATE Cars SET name = ?, year = ?, color = ?, hp = ?, description = ? WHERE id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1,item.getName());
            stmt.setString(2,item.getYear());
            stmt.setString(3,item.getColor());
            stmt.setInt(4,item.gethP());
            stmt.setString(5,item.getDescription());
            stmt.setInt(6,id);
            stmt.executeUpdate();
            return getById(id);
        } catch (Exception e) {
            throw new CarException("Greska pri izmjeni podataka!");
        }
    }


    /**
     * Method for deleting car from database with matching id
     * @param id
     */
    @Override
    public void delete(int id) {
        String query = "DELETE FROM Cars WHERE id = ?";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setInt(1,id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return All cars from database
     */
    @Override
    public List<Car> getAll() {
        String query = "SELECT * FROM Cars";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            ArrayList<Car> carArrayList = new ArrayList<Car>();
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Car car = new Car();
                car.setId(rs.getInt("id"));
                car.setName(rs.getString("name"));
                car.setYear(rs.getString("year"));
                car.setColor(rs.getString("color"));
                car.sethP(rs.getInt("hp"));
                car.setDescription(rs.getString("description"));
                carArrayList.add(car);
            }
            rs.close();
            return carArrayList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
