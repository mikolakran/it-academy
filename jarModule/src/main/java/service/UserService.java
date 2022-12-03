package service;

import dao.UserDAO;
import entity.User;
import exception.CatchingCauseException;

public class UserService {

    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getUser(long id) throws CatchingCauseException {
        return userDAO.get(id);
    }

    public User getByName(String name) throws CatchingCauseException {
        return userDAO.getByName(name);
    }
}
