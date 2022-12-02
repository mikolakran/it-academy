package dao.impl;

import dao.UserDAO;
import entity.User;
import exception.CatchingCauseException;
import exception.LoginException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
class UserDAOImplTest {
    @Autowired
    private UserDAO userDAO = new UserDAOImpl();

    private User user;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        user = new User("Nikolai","123","mikola@mail.ru","user");
    }

    @org.junit.jupiter.api.Test
    void save() {
        try {
            userDAO.save(user);
        } catch (LoginException | CatchingCauseException e) {
            throw new RuntimeException(e);
        }
    }
}