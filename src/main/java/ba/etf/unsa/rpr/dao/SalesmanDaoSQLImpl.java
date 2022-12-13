package ba.etf.unsa.rpr.dao;

import ba.etf.unsa.rpr.domain.Category;
import ba.etf.unsa.rpr.domain.Salesman;
import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SalesmanDaoSQLImpl implements SalesmanDao{

    private Connection connection;


    /**
     * Default constructor
     * makes database connection
     * username and password are hidden
     */
    public SalesmanDaoSQLImpl(){
        try{
            FileReader reader = new FileReader("db.properties");
            Properties property = new Properties();
            property.load(reader);
            this.connection = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/"+property.getProperty("username"),property.getProperty("username"),property.getProperty("password"));
        } catch (Exception  e) {
            e.printStackTrace();
        }
    }


    /**
     * @param id of salesman
     * @return Salesman with searched id
     */
    @Override
    public Salesman getById(int id) {
        String query = "SELECT * FROM Salesman where id = ?";
        try{
            PreparedStatement stmt  = this.connection.prepareStatement(query);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Salesman salesman = new Salesman();
                salesman.setId(rs.getInt("id"));
                salesman.setName(rs.getString("name"));
                salesman.setSurname(rs.getString("surname"));
                salesman.setNumber(rs.getString("number"));
                salesman.setPassword(rs.getString("password"));
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
     * Method for searching Salesman by his/her name, pass
     * @param name of Salesman
     * @param password of Salesman
     * @return  Salesman with given par. or null
     */
    public Salesman getByNamePass(String name, String password) {
        String query = "SELECT * FROM Salesman where name = ? and password = ?";
        try{
            PreparedStatement stmt  = this.connection.prepareStatement(query);
            stmt.setString(1,name);
            stmt.setString(2,password);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Salesman salesman = new Salesman();
                salesman.setId(rs.getInt("id"));
                salesman.setName(rs.getString("name"));
                salesman.setSurname(rs.getString("surname"));
                salesman.setNumber(rs.getString("number"));
                salesman.setPassword(rs.getString("password"));
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
     * Insert salesman in database with given values
     * @param item
     */
    @Override
    public void insert(Salesman item) {
        String query = "INSERT INTO Salesman (name,surname,number,password) values (?, ?, ?, ?)";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1,item.getName());
            stmt.setString(2,item.getSurname());
            stmt.setString(3,item.getNumber());
            stmt.setString(4,item.getPassword());
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method deletes Salesman from database based on passed id
     * @param id
     */
    @Override
    public void delete(int id) {
        String query = "DELETE FROM Salesman WHERE id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setInt(1,id);
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that gives List of all Salesmen in database
     * @return list of Salesmen
     */
    @Override
    public List<Salesman> getAll() {
        String query = "SELECT * FROM Salesman";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ArrayList<Salesman> salesmen = new ArrayList<Salesman>();
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Salesman salesman = new Salesman();
                salesman.setId(rs.getInt("id"));
                salesman.setName(rs.getString("name"));
                salesman.setSurname(rs.getString("surname"));
                salesman.setNumber("number");
                salesman.setPassword("password");
                salesmen.add(salesman);
            }
            rs.close();
            return salesmen;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Method that updates salesman with given id
     * @param item / salesman from which parameters are taken
     * @param id of salesman to update
     * @return updated salesman
     */
    @Override
    public Salesman update(Salesman item,int id) {
        String query = "UPDATE Salesman SET name = ?, surname = ?, number = ?, password = ? WHERE id = ?";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1,item.getName());
            stmt.setString(2,item.getSurname());
            stmt.setString(3,item.getNumber());
            stmt.setString(4,item.getPassword());
            stmt.setInt(5,id);
            stmt.executeUpdate(query);
            return getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
