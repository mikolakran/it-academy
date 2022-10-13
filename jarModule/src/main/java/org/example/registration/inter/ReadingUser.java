package org.example.registration.inter;

import org.example.registration.user.User;
import org.json.simple.JSONObject;

import java.util.List;

public interface ReadingUser {
    User getUserByKey(String key);
    String getUserByKeyTable(String key, String user_table);
    JSONObject getAllUser();

    long getIdMax();

    List<User> getListIdUsers();
}
