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
     * Method that gives List of all users in database
     * @return list of users
     */
    @Override
    public List<User> getAll() throws UserException {
        String query = "SELECT * FROM Users";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ArrayList<User> users = new ArrayList<User>();
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setAdmin(rs.getInt("admin"));
                users.add(user);
            }
            rs.close();
            return users;
        } catch (SQLException e) {
            throw new UserException("Greska pri dohvacanju podataka!");
        }
    }


    /**
     * Method that updates users with given id
     * @param item / user from which parameters are taken
     * @param id of user to update
     * @return updated user
     */
    @Override
    public User update(User item, int id) throws UserException {
        String query = "UPDATE Users SET name = ?, password = ? WHERE id = ?";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1,item.getName());
            stmt.setString(2,item.getPassword());
            stmt.setInt(3,id);
            stmt.executeUpdate();
            stmt.close();
            return getById(id);
        } catch (Exception e) {
            throw new UserException("Greska pri izmjeni!");
        }
    }

    /**
     * Help method that gets user by name and password
     * @param name of user
     * @param password of user
     * @return user
     * @throws UserException user defined exc
     */
    public User getByNamePass(String name, String password) throws UserException {
        String query = "SELECT * FROM Users where name = ? AND password = ?";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1,name);
            stmt.setString(2,password);
            ResultSet rs = stmt.executeQuery();
            User user = new User();
            if(rs.next()){
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setAdmin(rs.getInt("admin"));
            }
            rs.close();
            return user;
        } catch (SQLException e) {
            throw new UserException("Greska pri dohvacanju podataka korisnika!");
        }

    }

}
