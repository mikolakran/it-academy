package org.example.registration.json;

import org.example.registration.inter.GetDateBaseUser;
import org.example.registration.user.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
public class ReadingJson implements GetDateBaseUser {
    private final User user = new User();
    public File file;
    private JSONParser jsonParser;
    private JSONObject jsonObject1;
    private JSONObject jsonObject2;
    private JSONObject jsonObject11;
    public ReadingJson(File file) {
        this.file = file;
    }

    @Override
    public User get_user_date_base(String key) {
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
    public String get_user_table_date_base(String key, String table) {
        jsonParser = new JSONParser();
        try (FileReader fileReader = new FileReader(file)){
            jsonObject1 = (JSONObject) jsonParser.parse(fileReader);
            jsonObject2 = (JSONObject) jsonObject1.get("people");
            jsonObject11 = (JSONObject) jsonObject2.get(key);
            set_user_table(table);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return set_string_user_table(table);
    }

    @Override
    public JSONObject get_all_json_date_base() {
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
    private String set_string_user_table(String table) {
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

    private void set_user_table(String table) {
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
