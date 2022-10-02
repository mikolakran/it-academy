package org.example.registration.json;

import org.example.registration.inter.ReadingUser;
import org.example.registration.user.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
public class GetUser implements ReadingUser {
    private final User user = new User();
    public File file;
    private JSONParser jsonParser;
    private JSONObject jsonObject1;
    private JSONObject jsonObject2;
    private JSONObject jsonObject11;
    public GetUser(File file) {
        this.file = file;
    }

    @Override
    public User getUserByKey(String key) { // достаю user по id key
         jsonParser = new JSONParser();
        try (FileReader fileReader = new FileReader(file)){
             jsonObject1 = (JSONObject) jsonParser.parse(fileReader);
             jsonObject2 = (JSONObject) jsonObject1.get("people");
             jsonObject11 = (JSONObject) jsonObject2.get(key);
            long id = (long) jsonObject11.get("id");
            user.setId(id);
            user.setUsername((String) jsonObject11.get("username"));
            user.setPassword((String) jsonObject11.get("password"));
            user.setEmail((String) jsonObject11.get("email"));
            user.setRole((String) jsonObject11.get("role"));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public String getUserByKeyTable(String key, String table) { // достаю user по id key и name table
        jsonParser = new JSONParser();
        try (FileReader fileReader = new FileReader(file)){
            jsonObject1 = (JSONObject) jsonParser.parse(fileReader);
            jsonObject2 = (JSONObject) jsonObject1.get("people");
            jsonObject11 = (JSONObject) jsonObject2.get(key);
            setUserTable(table);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return setStringUserTable(table);
    }

    @Override
    public JSONObject getAllUser() { //достаю всех user
        jsonParser = new JSONParser();
        try {
            FileReader fileReader = new FileReader(file);
            jsonObject1 = (JSONObject) jsonParser.parse(fileReader);
                jsonObject1.get("people");
                fileReader.close();
        } catch (ParseException | IOException e) {
            System.out.println("null");
        }
        return jsonObject1;
    }
    private String setStringUserTable(String table) {
        String table2 = null;
        switch (table) {
            case "username":
                table2 = user.getUsername();
                break;
            case "password":
                table2 = user.getPassword();
                break;
            case "email":
                table2 = user.getEmail();
                break;
            case "role":
                table2 = user.getRole();
                break;
        }
        return table2;
    }

    private void setUserTable(String table) {
        switch (table) {
            case "username":
                user.setUsername((String) jsonObject11.get(table));
                break;
            case "password":
                user.setPassword((String) jsonObject11.get(table));
                break;
            case "email":
                user.setEmail((String) jsonObject11.get(table));
                break;
            case "role":
                user.setRole((String) jsonObject11.get(table));
                break;
        }
    }

}
