package ba.etf.unsa.rpr.business;

import ba.etf.unsa.rpr.dao.UserDao;
import ba.etf.unsa.rpr.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Author Nedim Krupalija
 */

class UserDaoTest {
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