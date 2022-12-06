package ba.etf.unsa.rpr.dao;

import ba.etf.unsa.rpr.domain.Cars;
import ba.etf.unsa.rpr.domain.Category;
import ba.etf.unsa.rpr.domain.Salesman;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CarDao interface SQL implementation
 * @author Nedim Krupalija
 */


public class CarDaoSQLImpl implements CarDao {
    private Connection conn;
    public CarDaoSQLImpl()
    {
        try{
            this.conn = DriverManager.getConnection("jdbc://sql7.freemysqlhosting.net:3306/abc","abc","abc");
        } catch (SQLException e) {
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
        String query = "SELECT * FROM cars where id = ?";
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

    @Override
    public Cars insert(Cars item) {
        return null;
    }

    @Override
    public Cars update(Cars item) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Cars> getAll() {

    }
}
