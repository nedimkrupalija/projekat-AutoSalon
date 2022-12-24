package ba.etf.unsa.rpr.dao;

import ba.etf.unsa.rpr.domain.Car;
import ba.etf.unsa.rpr.exception.CarException;

import java.io.FileReader;
import java.sql.*;
import java.util.*;

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
            car.setYear(rs.getString("year"));
            car.setColor(rs.getString("color"));
            car.sethP(rs.getInt("hp"));
            car.setDescription(rs.getString("description"));
            return car;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * ORM - object to sql query
     * @param object to map
     * @return map - String/Object
     */
    @Override
    public Map<String, Object> object2row(Car object) {
        Map<String, Object> row = new TreeMap<String, Object>();
        row.put("id", object.getId());
        row.put("name",object.getName());
        row.put("year",object.getYear());
        row.put("color",object.getColor());
        row.put("hp",object.gethP());
        row.put("description",object.getDescription());
        return row;
    }
}
