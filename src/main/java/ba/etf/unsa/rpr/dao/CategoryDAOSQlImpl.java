package ba.etf.unsa.rpr.dao;

import ba.etf.unsa.rpr.domain.Cars;
import ba.etf.unsa.rpr.domain.Category;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
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
            FileReader reader = new FileReader("db.properties");
            Properties property = new Properties();
            property.load(reader);
            this.conn = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/"+property.getProperty("username"),property.getProperty("username"),property.getProperty("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param id to look for
     * @return Specific category seached by id
     */
    @Override
    public Category getById(int id) {
        String query = "SELECT * FROM Category where id = ?";
        try{
            PreparedStatement stmt  = this.conn.prepareStatement(query);
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
     * Insert category item in database
     * @param item
     */
    @Override
    public void insert(Category item) throws SQLException {
        String query = "INSERT INTO Category (name) values (?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1,item.getName());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * Updates specific Category item
     * @param item from which attributes are taken
     * @param id of item to change
     * @return  updated item
     */
    @Override
    public Category update(Category item, int id) {
        String query = "UPDATE Category SET name = ? WHERE id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1,item.getName());
            stmt.setInt(2,id);
            stmt.executeUpdate();
            return getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Delete item from database based on id
     * @param id
     */
    @Override
    public void delete(int id) {
        String query = "DELETE FROM Category WHERE id = ?";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setInt(1,id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return list of all categories from
     */
    @Override
    public List<Category> getAll() {
        String query = "SELECT * FROM Category";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            ArrayList<Category> categories = new ArrayList<Category>();
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                categories.add(category);
            }
            rs.close();
            return categories;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getNumberWithName(String name) {
        String query = "SELECT COUNT(*) FROM Category WHERE lower(name)=?";
        try{
            PreparedStatement stmt  = this.conn.prepareStatement(query);
            stmt.setString(1,name);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Category category = new Category();
                int count = rs.getInt(1);
                rs.close();
                return count;
            }
            else return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
