package ba.etf.unsa.rpr.dao;

import ba.etf.unsa.rpr.domain.Cars;
import ba.etf.unsa.rpr.domain.Category;
import ba.etf.unsa.rpr.domain.Salesman;

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
     * Return array of cars based on category
     * @param category
     * @return
     */
    @Override
    public List<Cars> seachByCategory(Category category) {
        String query = "SELECT * FROM Cars where category_fk = ?";

        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setInt(1,category.getId());
            ResultSet rs = stmt.executeQuery();
            ArrayList<Cars> carsArrayList = new ArrayList<Cars>();
            while(rs.next()){
                Cars car = new Cars();
                car.setModel(rs.getString("model"));
                car.setMake(rs.getString("make"));
                car.setId(rs.getInt("id"));
                car.setCategory(category);
                car.setSalesman(getSalesmanById(car.getId()));
                car.setColor(rs.getString("color"));
                car.sethP(rs.getInt("hp"));
                car.setYear(rs.getString("year"));
                carsArrayList.add(car);
            }
            rs.close();
            return carsArrayList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    return null;
    }



    /**
     * Methods returns salesman for given id of car
     * @param id of car
     * @return
     */
    public Salesman getSalesmanById(int id){
        String query = "SELECT * FROM Salesman where id = (SELECT c.salesman_fk FROM Cars c WHERE c.id = ?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Salesman salesman = new Salesman();
                salesman.setId(rs.getInt("id"));
                salesman.setName(rs.getString("name"));
                salesman.setSurname(rs.getString("surname"));
                salesman.setNumber(rs.getString("number"));
                rs.close();
                return salesman;
            }
            else return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * Method that gives category for some id of car,
     * usefull for other methods that need to set Category
     * @param id of
     * @return
     */
    public Category getCategoryById(int id){
        String query = "SELECT * FROM Category where id = (SELECT c.category_fk FROM Cars c WHERE c.id = ?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                rs.close();
                return category;
            }
            else return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Method that returns all information about a car from DB
     * based on ID
     * @param id of car
     * @return
     */
    @Override
    public Cars getById(int id) {
        String query = "SELECT * FROM Cars where id = ?";
        try{
            PreparedStatement stmt  = this.conn.prepareStatement(query);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Cars car = new Cars();
                car.setId(rs.getInt("id"));
                car.setMake(rs.getString("make"));
                car.setModel(rs.getString("model"));
                car.setYear(rs.getString("year"));
                car.setColor(rs.getString("color"));
                car.sethP(rs.getInt("hp"));
                car.setCategory(getCategoryById(id));
                car.setSalesman(getSalesmanById(id));
                rs.close();
                return car;
            }
            else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return null;
    }


    /**
     * Method that inserts given car in database
     * @param item for insertion in database
     */
    @Override
    public void insert(Cars item) {
        String query = "INSERT INTO Cars (make,model,year,color,hp,salesman_fk,category_fk) values (?, ?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1,item.getMake());
            stmt.setString(2,item.getModel());
            stmt.setString(3,item.getYear());
            stmt.setString(4,item.getColor());
            stmt.setInt(5,item.gethP());
             stmt.setInt(6,item.getSalesman().getId());
            stmt.setInt(7,item.getCategory().getId());
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param item
     * @return updated car
     */

    @Override
    public Cars update(Cars item, int id) {
        String query = "UPDATE Cars SET make = ?, model = ?, year = ?, color = ?, hp = ?, salesman_fk = ?, category_fk = ? WHERE id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1,item.getMake());
            stmt.setString(2,item.getModel());
            stmt.setString(3,item.getYear());
            stmt.setString(4,item.getColor());
            stmt.setInt(5,item.gethP());
            stmt.setInt(6,item.getSalesman().getId());
            stmt.setInt(7,item.getCategory().getId());
            stmt.setInt(8,id);
            stmt.executeUpdate(query);
            return getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Note: Not very efficient
     * @return All cars from database
     */
    @Override
    public List<Cars> getAll() {
        String query = "SELECT * FROM Cars";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            ArrayList<Cars> carsArrayList = new ArrayList<Cars>();
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Cars car = new Cars();
                car.setId(rs.getInt("id"));
                car.setMake(rs.getString("make"));
                car.setModel(rs.getString("model"));
                car.setYear(rs.getString("year"));
                car.setColor(rs.getString("color"));
                car.sethP(rs.getInt("hp"));
                car.setCategory(getCategoryById(car.getId()));
                car.setSalesman(getSalesmanById(car.getId()));
                carsArrayList.add(car);
            }
            rs.close();
            return carsArrayList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
