package service;


import dao.UserDAO;
import entity.User;
import exception.CatchingCauseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

/*    @Mock
    private UserDAO userDAO;*/

    private UserDAO userDAO = mock(UserDAO.class);

    private UserService userService;
    private User user;

    @Before
    public void setUp() {
        userService = new UserService(userDAO);
         user = new User(1, "radfar", "1234", "nikolai@mail.ru", "user");
        try {
            when(userDAO.get(1L)).thenReturn(user);
        } catch (CatchingCauseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getUser() {
        try {
            User user1 = userService.getUser(1);
            verify(userDAO).get(1L);
            assertEquals(user.getId(),user1.getId());
        } catch (CatchingCauseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public  void getByName() {
    }
}