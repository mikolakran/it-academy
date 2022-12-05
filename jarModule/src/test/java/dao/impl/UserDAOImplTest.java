package dao.impl;

import configurations.AppConfig;
import dao.UserDAO;
import entity.User;
import exception.CatchingCauseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class UserDAOImplTest {

    @Autowired
    private UserDAO userDAO;

    private User user;

    @Before
    public void setUp() {
        user = new User("Nikolai10", "1234", "mikola10@mail.ru", "user");
    }

    @Test
    public void getByName() {
        try {
            List<User> listUsers = userDAO.getListUsers();
            listUsers.forEach(user1 -> {
                if (user1.getUserName().equals(user.getUserName())){
                    user = user1;
                }
            });
            assertEquals(user.toString(),userDAO.getByName("Nikolai10").toString());
        } catch (CatchingCauseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getListUsers() {
        try {
            assertNotNull(userDAO.getListUsers());
        } catch (CatchingCauseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getRoleData() {
        try {
            assertEquals("admin",userDAO.getRoleData("admin").getRole());
        } catch (CatchingCauseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getRoleUser() {
        assertEquals("user",userDAO.getRole(user));
    }

    @Test
    public void getRoleAdmin() {
        user.setEmail("mikolakran@gmail.com");
        assertEquals("admin",userDAO.getRole(user));
    }
}