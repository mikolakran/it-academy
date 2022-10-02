package org.example.registration.check;

import org.example.registration.inter.CheckUserInterface;
import org.example.registration.inter.ReadingUser;
import org.example.registration.json.GetUser;
import org.example.registration.user.User;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CheckUser implements CheckUserInterface {
    private boolean exist = false;

    /*Check User на совпадение*/
    @Override
    public boolean IsExistUser(User user, List<String> id, int count, File file) {
        ReadingUser readingJson = new GetUser(file);
        if (readingJson.getAllUser() != null && !exist) {
            // проверка userName,password,email
            if (isValidationPasswordOrUserName(user.getPassword()) == null ||
                    isValidationPasswordOrUserName(user.getUserName()) == null ||
                    isValidationEmail(user.getEmail()) == null) {
                exist = true;
            } else {
                JSONObject jsonObject2 = readingJson.getAllUser();
                JSONObject jsonObject3 = (JSONObject) jsonObject2.get("people");
                JSONObject jsonObject4;
                boolean b = false;
                for (int i = 0; i < count; i++) {
                    for (String s : id) {
                        jsonObject4 = (JSONObject) jsonObject3.get(s);
                        if (user.getUserName().equals(jsonObject4.get("userName")) ||
                                user.getPassword().equals(jsonObject4.get("password")) ||
                                user.getEmail().equals(jsonObject4.get("email"))) {
                            exist = true;
                            b = true;
                        }
                    }
                }
                if (b) {
                    isUserExist(user);
                }
            }
        }
        return exist;
    }

    @Override
    public void setExistUser(boolean exist) {
        this.exist = exist;
    }

    @Override
    public String isValidationPasswordOrUserName(String passOrEmail) {
        Pattern pattern = Pattern.compile("^[\\w\\S]{3,16}");
        Matcher matcher = pattern.matcher(passOrEmail);
        if (matcher.find()) {
            passOrEmail = matcher.group();
        } else {
            passOrEmail = null;
            System.out.println("not true password or userName");
        }
        return passOrEmail;
    }

    @Override
    public String isValidationEmail(String email) {
        Pattern pattern = Pattern.compile("[a-zA-Z\\d]{3,16}@[a-zA-Z\\d\\S]+\\.[a-zA-Z\\d]{2,3}");
        Matcher matcher = pattern.matcher(email);
        if (matcher.find()) {
            email = matcher.group();
        } else {
            email = null;
            System.out.println("not true email");
        }
        return email;
    }

    private void isUserExist(User user) {
        System.out.println("user exist id = " + user.getId());
    }

}
