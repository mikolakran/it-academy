package dao;

import configurations.AppConfig;
import entity.User;
import exception.CatchingCauseException;
import exception.LoginException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class BaseDAOTest {

    @Autowired
    private UserDAO userDAO;

    private User user;

    @Before
    public void setUp() {
        user = new User("Nikolai10", "1234", "mikola10@mail.ru", "user");
    }

    @Test
    @Ignore
    public void save() {
        try {
            userDAO.save(user);
        } catch (LoginException | CatchingCauseException e) {
            fail();
        }
    }

    @Test
    @Ignore
    public void throwsExceptionForSave1() {
        assertThrows(LoginException.class, () -> userDAO.save(user));
    }

    @Test
    @Ignore
    public void throwsExceptionForSave2() {
        assertThrows(CatchingCauseException.class, () -> userDAO.save(user));
    }

    @Test
    @Ignore
    public void get() {
        try {
            User result1 = userDAO.getByName("Nikolai10");
            assertEquals(result1.toString(), userDAO.get(result1.getId()).toString());
        } catch (CatchingCauseException e) {
            fail();
        }
    }

    @Test
    @Ignore
    public void throwsExceptionForGet() {
        assertThrows(CatchingCauseException.class, () -> userDAO.get(1L));
    }

    @Test
    @Ignore
    public void update() {
        try {
            User result1 = userDAO.getByName("Nikolai10");
            result1.setEmail("mikola12@mail.ru");
            userDAO.update(result1);
        } catch (LoginException | CatchingCauseException e) {
            fail();
        }
    }

    @Test
    @Ignore
    public void throwsExceptionForUpdate() {
        assertThrows(LoginException.class,()-> userDAO.update(user));
    }

    @Test
    @Ignore
    public void delete() {
        try {
            User result1 = userDAO.getByName("Nikolai10");
            userDAO.delete(result1.getId());
            User result2 = userDAO.getByName("Nikolai10");
            assertNull(result2.getUserName());
        } catch (CatchingCauseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Ignore
    public void ThrowsExceptionDelete(){
        assertThrows(CatchingCauseException.class,()-> userDAO.delete(1231L));
    }
}