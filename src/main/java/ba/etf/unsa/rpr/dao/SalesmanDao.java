package ba.etf.unsa.rpr.dao;

import ba.etf.unsa.rpr.domain.Salesman;

/**
 * Dao interface for salesman
 * @autor Nedim Krupalija
 */

public interface SalesmanDao extends Dao<Salesman> {
    public Salesman getByIdPass(int id, String password);
}
