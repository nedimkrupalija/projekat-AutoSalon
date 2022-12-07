package ba.etf.unsa.rpr.dao;

import ba.etf.unsa.rpr.domain.Category;
import ba.etf.unsa.rpr.domain.Salesman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
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



    @Override
    public Salesman getById(int id) {
        return null;
    }

    @Override
    public void insert(Salesman item) {
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Salesman> getAll() {
        return null;
    }

    @Override
    public Salesman update(Salesman item,int id) {
        return null;
    }
}
