package dao.impl;

import configurations.AppConfig;
import dao.UserDAO;
import entity.User;
import exception.CatchingCauseException;
import exception.LoginException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class UserDAOImplTest {

    @Autowired
    private UserDAO userDAO;

    private User user;

    @Before
    public void setUp() {
        user = new User("Nikolai9", "1234", "mikola9@mail.ru", "user");
    }

    @Test
//    @Ignore
    public void save() {
        try {
            userDAO.save(user);
        } catch (LoginException | CatchingCauseException  e) {
            System.out.println("e.getMessage() = " + e.getMessage());
            System.out.println("e = ");
        }
//        assertThrows(LoginException.class,()-> userDAO.save(user));
//        assertThrows(CatchingCauseException.class,()-> userDAO.save(user));
    }
}