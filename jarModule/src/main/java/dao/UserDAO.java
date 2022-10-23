package dao;

import entity.User;

import java.util.List;

public interface UserDAO extends DAO<User>{
    User getByName(String name);
    List<User> getListUsers();
    String getRole(User user);
}
