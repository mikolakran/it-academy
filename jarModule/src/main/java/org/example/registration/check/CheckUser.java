package org.example.registration.check;

import org.example.registration.inter.CheckUser_inter;
import org.example.registration.inter.GetDateBaseUser;
import org.example.registration.json.ReadingJson;
import org.example.registration.user.User;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.List;


public class CheckUser implements CheckUser_inter {
    private boolean exist = false;
    @Override
    public boolean exist_user(User user, List<String> id, int count, File file) {
        GetDateBaseUser readingJson = new ReadingJson(file);
        if (readingJson.get_all_json_date_base()!=null && !exist) {
            JSONObject jsonObject2 = readingJson.get_all_json_date_base();
            JSONObject jsonObject3 = (JSONObject) jsonObject2.get("people");
            JSONObject jsonObject4;

            for (int i = 0; i < count; i++) {
                for (String s : id) {
                    jsonObject4 = (JSONObject) jsonObject3.get(s);
                    if (user.getUsername().equals(jsonObject4.get("username")) ||
                            user.getPassword().equals(jsonObject4.get("password")) ||
                            user.getEmail().equals(jsonObject4.get("email"))) {
                        exist = true;
                    }

                }
            }
        }
        return exist;
    }

    @Override
    public void setExist(boolean exist) {
        this.exist = exist;
    }

}
