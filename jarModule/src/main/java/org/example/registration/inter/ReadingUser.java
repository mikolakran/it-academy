package org.example.registration.inter;

import org.example.registration.user.User;

import java.util.List;

public interface ReadingUser {
    User getUserByKey(int key);
    User getUserByKeyTableId(String id);
    User getUserByKeyTableName(String name);
    List<User> getListUsers();

}
