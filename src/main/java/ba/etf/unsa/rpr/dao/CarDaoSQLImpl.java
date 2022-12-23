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


    @Override
    public Car row2object(ResultSet rs) {
        return null;
    }

    @Override
    public Map<String, Object> object2row(Car object) {
        return null;
    }
}
