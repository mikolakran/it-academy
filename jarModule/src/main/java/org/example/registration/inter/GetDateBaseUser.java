package org.example.registration.inter;

import org.example.registration.user.User;
import org.json.simple.JSONObject;

public interface GetDateBaseUser {
    User get_user_date_base(String key);
    String get_user_table_date_base(String key, String user_table);
    JSONObject get_all_json_date_base();

}
