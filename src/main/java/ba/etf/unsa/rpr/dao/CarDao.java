package ba.etf.unsa.rpr.dao;

import ba.etf.unsa.rpr.domain.Cars;
import ba.etf.unsa.rpr.domain.Category;

import java.util.List;

/**
 * Dao interface for cars
 * @author Nedim Krupalija
 */

public interface CarDao extends Dao<Cars> {

    /**
     * Method for searching for cars of certain category
     * @param category
     * @return list of cars of given category
     */
    List<Cars> seachByCategory(Category category);

    Cars update(Cars item,int id);

    int countCategories(int id);
}
