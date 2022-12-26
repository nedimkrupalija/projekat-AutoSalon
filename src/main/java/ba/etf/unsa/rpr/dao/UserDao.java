package ba.etf.unsa.rpr.dao;

import ba.etf.unsa.rpr.domain.User;
import ba.etf.unsa.rpr.exception.UserException;

/**
 * Dao interface for salesman
 * @autor Nedim Krupalija
 */

public interface UserDao extends Dao<User> {

    public User getByNamePass(String name, String password) throws UserException;
}
