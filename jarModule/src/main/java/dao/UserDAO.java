package dao;

import entity.User;
import exception.CatchingCauseException;

import java.util.List;

public interface UserDAO extends DAO<User,Long> {
    User getByName(String name) throws CatchingCauseException;

    List<User> getListUsers() throws CatchingCauseException;

    User getRoleData(String idAdmin) throws CatchingCauseException;

    String getRole(User user);
}
