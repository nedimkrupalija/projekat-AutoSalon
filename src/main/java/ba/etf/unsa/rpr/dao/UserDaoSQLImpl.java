package ba.etf.unsa.rpr.dao;

import ba.etf.unsa.rpr.domain.User;
import ba.etf.unsa.rpr.exception.UserException;

import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class UserDaoSQLImpl extends AbstractDao<User> implements UserDao {

    private Connection connection;




    /**
     * Default constructor
     * makes database connection
     * username and password are hidden
     */
    public UserDaoSQLImpl(){
        super("Users");
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
            if(user.getName()==null) throw new UserException("Nepostojeci korisnik!");
            return user;
        } catch (SQLException e) {
            throw new UserException("Greska pri dohvacanju podataka korisnika!");
        }

    }

    @Override
    public User row2object(ResultSet rs) {
        return null;
    }

    @Override
    public Map<String, Object> object2row(User object) {
        return null;
    }
}
