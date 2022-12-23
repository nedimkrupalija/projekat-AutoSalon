package ba.etf.unsa.rpr.dao;

import ba.etf.unsa.rpr.domain.Car;
import ba.etf.unsa.rpr.exception.CarException;

import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * CarDao interface SQL implementation
 * @author Nedim Krupalija
 */


public class CarDaoSQLImpl extends AbstractDao<Car> implements CarDao {
    private Connection conn;

    /**
     * Constructor for CarDao implementation, makes DB connection, username and password hidden
     */
    public CarDaoSQLImpl()
    {
        super("Cars");
    }

    /**
     * ORM - row2car mapping
     * Sets parems. of car returned from db
     * @param rs resultset
     * @return Car object
     */
    @Override
    public Car row2object(ResultSet rs) {
        try{
            Car car = new Car();
            car.setId(rs.getInt("id"));
            car.setName(rs.getString("name"));
            car.setColor(rs.getString("color"));
            car.sethP(rs.getInt("hp"));
            car.setDescription(rs.getString("desc"));
            car.setYear(rs.getString("year"));
            return car;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Map<String, Object> object2row(Car object) {
        return null;
    }
}
