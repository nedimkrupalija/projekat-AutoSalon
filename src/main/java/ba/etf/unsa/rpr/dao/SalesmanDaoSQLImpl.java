package ba.etf.unsa.rpr.dao;

import ba.etf.unsa.rpr.domain.Category;
import ba.etf.unsa.rpr.domain.Salesman;

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
        String query = "INSERT INTO Salesman (name,surname,number) values (?, ?, ?)";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1,item.getName());
            stmt.setString(2,item.getSurname());
            stmt.setString(3,item.getNumber());
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


    @Override
    public List<Salesman> getAll() {

    }

    @Override
    public Salesman update(Salesman item,int id) {
        return null;
    }
}
