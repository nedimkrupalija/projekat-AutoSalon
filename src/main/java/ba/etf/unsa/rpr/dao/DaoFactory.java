package ba.etf.unsa.rpr.dao;

/**
 * Factory for all DAO classes
 * @author Nedim Krupalija
 */
public class DaoFactory {

    private static final CarDao carDao = CarDaoSQLImpl.getInstance();

    private static final ReservationDao reservationDao = ReservationDAOSQlImpl.getInstance();

    private static final UserDao userDao = UserDaoSQLImpl.getInstance();

    private DaoFactory(){
    }
    public static CarDao carDao() {return carDao;}

    public static ReservationDao reservationDao() {return reservationDao;}
    public static UserDao userDao() {return userDao;}







}
