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
