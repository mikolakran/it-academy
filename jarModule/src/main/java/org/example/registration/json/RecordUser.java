package org.example.registration.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.registration.check.CheckUser;
import org.example.registration.inter.CheckUserInterface;
import org.example.registration.inter.WriteFileUser;
import org.example.registration.user.User;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecordUser implements WriteFileUser {

    private final JSONObject jsonObject = new JSONObject();
    private final JSONObject jsonObject1 = new JSONObject();
    private final ObjectMapper objectMapper = new ObjectMapper();
   public File file;
    private int count = 0;
    private final List<String> list = new ArrayList<>();
    private final CheckUserInterface checkUser = new CheckUser();

    public RecordUser(File file) {
        this.file = file;
    }

    @Override
    public void writeUser(User user) {
        if (user.getUsername().equals(user.getPassword())){
            checkUser.setExistUser(true);
        }
        if (!checkUser.IsExistUser(user,list,count,file)){ //проверка на совпадение userName,email,password
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
            checkUser.setExistUser(false);
            System.out.println("user exist");
        }
    }
}
