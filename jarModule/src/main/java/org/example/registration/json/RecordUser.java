package org.example.registration.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.registration.check.CheckUser;
import org.example.registration.inter.ReadingUser;
import org.example.registration.inter.exception.LoginException;
import org.example.registration.inter.CheckUserInterface;
import org.example.registration.inter.WriteFileUser;
import org.example.registration.user.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecordUser implements WriteFileUser {

    private final JSONObject jsonObject = new JSONObject();
    private final JSONObject jsonObject1 = new JSONObject();
    private final ObjectMapper objectMapper = new ObjectMapper();
   public File file;
    private int count = 0;
    private long idGetKey = 0;
    private final List<String> list = new ArrayList<>();
    private final CheckUserInterface checkUser = new CheckUser();

    public RecordUser(File file) {
        this.file = file;
    }

    @Override
    public void writeUser(User user) throws LoginException {
        if (user.getUserName().equals(user.getPassword())) {
            checkUser.setExistUser(true);
            throw new LoginException("userName and password same");
        }
        if (!checkUser.IsExistUser(user, file)) { //проверка на совпадение userName,email,password
            count++;
            ReadingUser readingUser = new GetUser(file);
            JSONObject allUser = readingUser.getAllUser();
            if (allUser!=null) {
                addUser(user, allUser);
            }else {
                jsonObject.put(user.getId(), user);
                list.add(String.valueOf(user.getId()));
                jsonObject1.put("people", jsonObject);
                objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                try {
                    objectMapper.writeValue(file, jsonObject1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            checkUser.setExistUser(false);
        }
    }

    private void addUser(User user, JSONObject allUser) {
        ReadingUser getDateBaseUser = new GetUser(file);
        idGetKey = getDateBaseUser.getIdMax( allUser);
        if (user.getId() <= idGetKey) {
                idGetKey++;
                user.setId(idGetKey);
            }
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject ;
        try {
            jsonObject = (JSONObject) jsonParser.parse(new FileReader(file));
            JSONObject jsonObject1 = (JSONObject) jsonObject.get("people");
            jsonObject1.put(user.getId(), user);
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            objectMapper.writeValue(file,jsonObject);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
