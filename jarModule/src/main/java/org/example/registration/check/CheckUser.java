package org.example.registration.check;

import org.example.registration.inter.CheckUserInterface;
import org.example.registration.inter.ReadingUser;
import org.example.registration.json.GetUser;
import org.example.registration.user.User;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.List;


public class CheckUser implements CheckUserInterface {
    private boolean exist = false;
    /*Check User на совпадение*/
    @Override
    public boolean IsExistUser(User user, List<String> id, int count, File file) {
        ReadingUser readingJson = new GetUser(file);
        if (readingJson.getAllUser()!=null && !exist) {
            JSONObject jsonObject2 = readingJson.getAllUser();
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
    public void setExistUser(boolean exist) {
        this.exist = exist;
    }

}
