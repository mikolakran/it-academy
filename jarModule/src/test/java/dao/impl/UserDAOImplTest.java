package dao.impl;

import configurations.AppConfig;
import dao.UserDAO;
import entity.User;
import exception.MyException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import repository.UserJpaRepository;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class UserDAOImplTest {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserJpaRepository userJpaRepository;

    private User user;
    private final String NAME = "Nikolai3";
    private final String PASSWORD = "*s9C#nFSNx#A";
    private final String EMAIL = "mikola3@mail.ru";
    private final String ROLE = "user";

    @Before
    public void setUp() {
        user = new User(NAME, PASSWORD, EMAIL, ROLE);
    }

    @Test
    @Ignore
    public void springTest(){
        userJpaRepository.findByEmail("mikolakran@gmail.com");
    }

    @Test
    @Ignore
    public void save() throws MyException {
        userDAO.save(user);
        User result = userDAO.getByName(user.getUserName());
        assertEquals(result.toString(), userDAO.getByEmail(EMAIL).toString());
    }

    @Test
    @Ignore
    public void saveAdmin() throws MyException {
        User admin = new User("mikolai",PASSWORD,"mikolakran2@gmail.com","admin");
        userDAO.save(admin);
        User result = userDAO.getByName(admin.getUserName());
        assertEquals(result.toString(), userDAO.getByEmail(admin.getEmail()).toString());

    }

    @Test
    @Ignore
    public void throwsExceptionSave(){
        assertThrows(MyException.class,()->userDAO.save(user));
    }

    @Test
    @Ignore
    public void throwsExceptionForValidationNameException() {
        user.setUserName("Ni");
        assertThrows(MyException.class, () -> userDAO.save(user));
    }

    @Test
    @Ignore
    public void throwsExceptionForValidationEmailException() {
        user.setEmail("mikola.mail.ru");
        assertThrows(MyException.class, () -> userDAO.save(user));
    }

    @Test
    @Ignore
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
    @Ignore
    public void update() throws MyException {
        User result1 = userDAO.getByName(NAME);
        result1.setUserName("Nikolai17");
        result1.setEmail("mikola17@mail.ru");
        userDAO.update(result1);
        assertEquals(result1.toString(), userDAO.getByName(result1.getUserName()).toString());
    }

    @Test
    @Ignore
    public void throwsUpdateExceptionEmail(){
        user.setEmail("mikola10.mail.ru");
        assertThrows(MyException.class,()->userDAO.update(user));
        user.setEmail("mikola10@mail.ru");
        assertThrows(MyException.class,()->userDAO.update(user));
    }

    @Test
    @Ignore
    public void throwsUpdateExceptionPassword(){
        user.setPassword("PASSWORD");
        assertThrows(MyException.class,()->userDAO.update(user));
    }

    @Test
    @Ignore
    public void throwsUpdateExceptionName(){
        user.setUserName("Mi");
        assertThrows(MyException.class,()->userDAO.update(user));
        user.setUserName("Nikolai10");
        assertThrows(MyException.class,()->userDAO.update(user));
    }

    @Test
    @Ignore
    public void delete() {
        User result2 = userDAO.get(userDAO.getByName(NAME).getId());
        userDAO.delete(result2.getId());
        User result3 = userDAO.getByName(result2.getUserName());
        assertNull(result3);
    }


    @Test
//    @Ignore
    public void getByName() {
        assertEquals(user.getUserName(), userDAO.getByName(NAME).getUserName());
    }

    @Test
//    @Ignore
    public void getByEmail(){
        assertEquals(user.getEmail(), userDAO.getByEmail(EMAIL).getEmail());
    }

    @Test
//    @Ignore
    public void getListUsers() {
        List<User> listUsers = userDAO.getListUsers();
        assertArrayEquals(listUsers.toArray(), userDAO.getListUsers().toArray());
    }

    @Test
//    @Ignore
    public void getRoleUser() {
        assertEquals(ROLE, userDAO.getRole(user));
    }

}