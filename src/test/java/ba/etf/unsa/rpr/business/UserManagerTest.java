package ba.etf.unsa.rpr.business;

import ba.etf.unsa.rpr.dao.AbstractDao;
import ba.etf.unsa.rpr.dao.DaoFactory;
import ba.etf.unsa.rpr.dao.UserDao;
import ba.etf.unsa.rpr.dao.UserDaoSQLImpl;
import ba.etf.unsa.rpr.domain.User;
import ba.etf.unsa.rpr.exception.CarException;
import ba.etf.unsa.rpr.exception.ReservationException;
import ba.etf.unsa.rpr.exception.UserException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Author Nedim Krupalija
 */

class UserManagerTest {
    @Mock
    UserDao userDao;
    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    public User createUser(){
        User user = new User();
        user.setName("Hamo");
        user.setPassword("123");
        user.setAdmin(0);
        return user;
    }
    @Test
    public void test1() throws Exception {
        Mockito.when(userDao.getById(1)).thenReturn(createUser());
        User u = userDao.getById(1);

        assert(u.getName().equals("Hamo"));
        assert(u.getPassword().equals("123"));
    }

    @Test
    public void test2() throws Exception{
        UserDao userDaoSpy = Mockito.spy(userDao);
        Mockito.when(userDaoSpy.getById(Mockito.anyInt())).thenReturn(createUser());
        User u = userDaoSpy.getById(100);
        System.out.println(u.getName());
    }
}