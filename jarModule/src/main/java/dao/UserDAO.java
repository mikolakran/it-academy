package dao;

import entity.User;
import exception.LoginException;

import java.util.List;

public interface UserDAO extends DAO<User>{
    User getByName(String name) throws LoginException;
    List<User> getListUsers() throws LoginException;
    String getRole(User user);
}
