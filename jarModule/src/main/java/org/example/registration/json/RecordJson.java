package org.example.registration.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.registration.check.CheckUser;
import org.example.registration.inter.CheckUser_inter;
import org.example.registration.inter.SetDateBaseUser;
import org.example.registration.user.User;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecordJson implements SetDateBaseUser {

    private final JSONObject jsonObject = new JSONObject();
    private final JSONObject jsonObject1 = new JSONObject();
    private final ObjectMapper objectMapper = new ObjectMapper();
   public File file;
    private int count = 0;
    private final List<String> list = new ArrayList<>();
    private final CheckUser_inter checkUser = new CheckUser();

    public RecordJson(File file) {
        this.file = file;
    }

    @Override
    public void set_all_date_base(User user) {
        if (user.getUsername().equals(user.getPassword())){
            checkUser.setExist(true);
        }
        if (!checkUser.exist_user(user,list,count,file)){
            count++;
            jsonObject.put(user.getId(),user);
            list.add(String.valueOf(user.getId()));
            jsonObject1.put("people", jsonObject);
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            try {
                objectMapper.writeValue(file, jsonObject1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            checkUser.setExist(false);
            System.out.println("user exist");
        }
    }

}
