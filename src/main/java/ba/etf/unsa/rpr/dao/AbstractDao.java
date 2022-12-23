package ba.etf.unsa.rpr.dao;

import ba.etf.unsa.rpr.domain.Idable;

import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;


/**
 * Abstract class for implementing all database operation on all tables
 * @param <T>
 */

public abstract class AbstractDao<T extends Idable> implements Dao<T> {
    private Connection conn;
    private String tableName;

    public Connection getConn() {
        return conn;
    }

    /**
     * Constructor for abstract dao
     * Makes connection onto db
     * @param tableName String name of table in database
     */
    public AbstractDao(String tableName){
            this.tableName = tableName;
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
     * Method for deleting object from db
     * @param id of object to delete
     */
   public void delete(int id){
        String query = "DELETE FROM " + this.tableName + " WHERE id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setInt(1,id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

   }




}
