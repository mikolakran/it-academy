package com.pvt.dao;



import com.pvt.entity.User;

import java.util.List;
import java.util.Set;

public interface UserDAO extends DAO<User,Long> {
    User getByName(String name);
    User getByEmail(String email);

    List<User> getListUsers();

    Set<User> getListUsersWhereIdTopic(long id);

    String getRole(User user);
}
