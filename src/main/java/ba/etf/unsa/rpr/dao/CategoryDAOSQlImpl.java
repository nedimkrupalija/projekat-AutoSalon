package ba.etf.unsa.rpr.dao;

import ba.etf.unsa.rpr.domain.Category;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;

public class CategoryDAOSQlImpl implements CategoryDao{

    private Connection conn ;

    /**
     * Default constructor for CategoryDao implementation, makes connection to database
     * Parameters hidden
     */
    public CategoryDAOSQlImpl(){
        try {
            FileReader reader = new FileReader("properties.db");
            Properties property = new Properties();
            property.load(reader);
            this.conn = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/"+property.getProperty("username"),property.getProperty("username"),property.getProperty("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Category getById(int id) {
        return null;
    }

    @Override
    public void insert(Category item) {

    }

    @Override
    public Category update(Category item, int id) {
        return null;
    }



    @Override
    public void delete(int id) {

    }

    @Override
    public List<Category> getAll() {
        return null;
    }
}
