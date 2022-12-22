package ba.etf.unsa.rpr.dao;

import ba.etf.unsa.rpr.domain.Idable;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;


/**
 * Abstract class for implementing all database operation on all tables
 * @param <T>
 */

public abstract class AbstractDao<T extends Idable> implements Dao<T> {
    private Connection conn;
    private String tableName;

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
}
