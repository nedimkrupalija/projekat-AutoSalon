package ba.etf.unsa.rpr.dao;

/**
 * Factory for all DAO classes
 * @author Nedim Krupalija
 */
public class DaoFactory {

    private static final CarDao carDao = new CarDaoSQLImpl();

    private static final ReservationDao reservationDao = new ReservationDAOSQlImpl();

    private static final UserDao userDao = new UserDaoSQLImpl();

    private DaoFactory(){

    }
    public static CarDao carDao() {return carDao;}

    public static ReservationDao reservationDao() {return reservationDao;}
    public static UserDao userDao() {return userDao;}







}
