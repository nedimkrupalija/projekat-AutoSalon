package ba.etf.unsa.rpr.dao;

import ba.etf.unsa.rpr.domain.Cars;
import ba.etf.unsa.rpr.domain.Category;

import java.sql.*;
import java.util.List;

public class CarDaoSQLImpl implements CarDao {
    private Connection conn;
    public CarDaoSQLImpl()
    {
        try{
            this.conn = DriverManager.getConnection("jdbc://sql7.freemysqlhosting.net:3306/abc","abc","abc");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cars> seachByCategory(Category category) {
        return null;
    }


    /**
     * Method that gives category for some id of car,
     * usefull for other methods that need to set Category
     * @param id of
     * @return
     */
    public Category getCategoryById(int id){
        String query = "SELECT * FROM Category where id = (SELECT c.category_fk FROM Cars c WHERE c.id = ?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
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

    @Override
    public Cars getById(int id) {

    }

    @Override
    public Cars insert(Cars item) {
        return null;
    }

    @Override
    public Cars update(Cars item) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Cars> getAll() {
        return null;
    }
}
