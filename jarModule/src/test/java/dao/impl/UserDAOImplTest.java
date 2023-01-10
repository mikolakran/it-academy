package dao.impl;

import configurations.AppConfig;
import dao.UserDAO;
import entity.User;
import exception.MyException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class UserDAOImplTest {

    @Autowired
    private UserDAO userDAO;

    private User user;
    private final String NAME = "Nikolai3";
    private final String PASSWORD = "*s9C#nFSNx#A";
    private final String EMAIL = "mikola3@mail.ru";
    private final String ROLE = "user";

    @Before
    public void setUp() throws MyException {
        user = new User(NAME, PASSWORD, EMAIL, ROLE);
        userDAO.save(user);
    }

    @Test
//    @Ignore
    public void save() {
        User result = userDAO.getByName(NAME);
        assertEquals(result.toString(), userDAO.getByEmail(EMAIL).toString());
    }

    @Test
//    @Ignore
    public void throwsExceptionSave() {
        assertThrows(MyException.class, () -> userDAO.save(user));
    }

    @Test
//    @Ignore
    public void throwsExceptionForValidationNameException() {
        user.setUserName("Ni");
        assertThrows(MyException.class, () -> userDAO.save(user));
    }

    @Test
//    @Ignore
    public void throwsExceptionForValidationEmailException() {
        user.setEmail("mikola.mail.ru");
        assertThrows(MyException.class, () -> userDAO.save(user));
    }

    @Test
//    @Ignore
    public void throwsExceptionForValidationPasswordException() {
        user.setPassword("sdasda");
        assertThrows(MyException.class, () -> userDAO.save(user));
    }

    @Test
//    @Ignore
    public void get() {
        User result1 = userDAO.getByName(NAME);
        assertEquals(result1.toString(), userDAO.get(result1.getId()).toString());
    }

    @Test
//    @Ignore
    public void update() throws MyException {
        User result1 = userDAO.getByName(NAME);
        result1.setUserName("Nikolai17");
        result1.setEmail("mikola17@mail.ru");
        userDAO.update(result1);
        assertEquals(result1.toString(), userDAO.getByName(result1.getUserName()).toString());
        user.setEmail("mikola17.mail.ru");
        assertThrows(MyException.class, () -> userDAO.update(user));
        user.setUserName("Mi");
        assertThrows(MyException.class, () -> userDAO.update(user));
        result1.setUserName(NAME);
        result1.setEmail(EMAIL);
        userDAO.update(result1);
        assertEquals(result1.toString(), userDAO.getByName(result1.getUserName()).toString());
    }

    @Test
//    @Ignore
    public void throwsUpdateExceptionPassword() {
        user.setPassword("PASSWORD");
        assertThrows(MyException.class, () -> userDAO.update(user));
    }

    @Test
//    @Ignore
    public void getByName() {
        assertEquals(user.getUserName(), userDAO.getByName(NAME).getUserName());
    }

    @Test
//    @Ignore
    public void getByEmail() {
        assertEquals(user.getEmail(), userDAO.getByEmail(EMAIL).getEmail());
    }

    @Test
//    @Ignore
    public void getListUsers() {
        List<User> listUsers = userDAO.getListUsers();
        assertArrayEquals(listUsers.toArray(), userDAO.getListUsers().toArray());
    }

    @Test
    public void getListUsersWhereIdTopic() {
        Set<User> listUsersWhereIdTopic = userDAO.getListUsersWhereIdTopic(1L);
        assertNotNull(listUsersWhereIdTopic);
    }

    @Test
//    @Ignore
    public void getRoleUser() {
        assertEquals(ROLE, userDAO.getRole(user));
    }

    @After
    public void delete() {
        User resultByName = userDAO.getByName(NAME);
        userDAO.delete(resultByName.getId());
        User resultByName2 = userDAO.getByName(NAME);
        assertNull(resultByName2);
    }

}