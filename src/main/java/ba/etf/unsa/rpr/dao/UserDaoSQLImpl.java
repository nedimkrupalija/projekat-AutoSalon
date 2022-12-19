package ba.etf.unsa.rpr.dao;

import ba.etf.unsa.rpr.domain.User;
import ba.etf.unsa.rpr.exception.UserException;

import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDaoSQLImpl implements UserDao {

    private Connection connection;


    /**
     * Default constructor
     * makes database connection
     * username and password are hidden
     */
    public UserDaoSQLImpl(){
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
     * @param id of user
     * @return User with searched id
     */
    @Override
    public User getById(int id) throws UserException {
        String query = "SELECT * FROM Users where id = ?";
        try{
            PreparedStatement stmt  = this.connection.prepareStatement(query);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setAdmin(rs.getInt("admin"));
                rs.close();
                return user;
            }
            else return null;
        } catch (SQLException e) {
            throw new UserException("Greska pri dohvacanju usera");
        }
    }


    /**
     * Insert user in database with given values
     * @param item
     */
    @Override
    public void insert(User item) throws UserException {
        String query = "INSERT INTO Users (name,password) values (?, ?)";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1,item.getName());
            stmt.setString(2,item.getPassword());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new UserException("Greska pri ubacivanju korisnika!");
        }
    }

    /**
     * Method deletes user from database based on passed id
     * @param id
     */
    @Override
    public void delete(int id) throws UserException {
        String query = "DELETE FROM Users WHERE id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setInt(1,id);
            stmt.executeUpdate();
        } catch (SQLException e) {
           throw new UserException("Greska pri brisanju");
        }
    }

    /**
     * Method that gives List of all Salesmen in database
     * @return list of Salesmen
     */
    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM Salesman";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ArrayList<User> salesmen = new ArrayList<User>();
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setNumber("number");
                user.setPassword("password");
                salesmen.add(user);
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
    public User update(User item, int id) {
        String query = "UPDATE Salesman SET name = ?, surname = ?, number = ?, password = ? WHERE id = ?";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1,item.getName());
            stmt.setString(2,item.getSurname());
            stmt.setString(3,item.getNumber());
            stmt.setString(4,item.getPassword());
            stmt.setInt(5,id);
            stmt.executeUpdate();
            stmt.close();
            return getById(id);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Greska pri update-u usera");
        }
        return null;
    }
}
