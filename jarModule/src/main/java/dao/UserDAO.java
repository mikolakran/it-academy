package dao;

import entity.Topic;
import entity.User;
import exception.CatchingCauseException;

import java.util.List;

public interface UserDAO extends DAO<User>{
    User getByName(String name) throws CatchingCauseException;
    List<User> getListUsers() throws CatchingCauseException;
    String getRole(User user);
}
