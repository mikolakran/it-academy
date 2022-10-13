package org.example.registration.check;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.registration.inter.ChangesUserInterface;
import org.example.registration.inter.ReadingUser;
import org.example.registration.json.GetUser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class ChangesUser implements ChangesUserInterface {
    private final ObjectMapper objectMapper = new ObjectMapper();
    public File file;

    public ChangesUser(File file) {
        this.file = file;
    }

    @Override
    public void changesUserBySelectionTable(String key, String key_table, String table, File file) { //изменяет user по выбор таблицы email,userName,password
        ReadingUser getDateBaseUser = new GetUser(file);
        String text = getDateBaseUser.getUserByKeyTable(key, key_table);
        if (text != null) {
            changesBySelectTable(key, key_table, table);
        }
    }

    @Override
    public void deleteUserByKey(String key, File file) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) jsonParser.parse(new FileReader(this.file));
            JSONObject jsonObject1 = (JSONObject) jsonObject.get("people");
            JSONObject jsonObject2 = (JSONObject) jsonObject1.remove(key);
            jsonObject.remove(jsonObject2);
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            objectMapper.writeValue(this.file, jsonObject);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void changesBySelectTable(String key, String keyTable, String text) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) jsonParser.parse(new FileReader(this.file));
            JSONObject jsonObject1 = (JSONObject) jsonObject.get("people");
            JSONObject jsonObject2 = (JSONObject) jsonObject1.get(key);
            jsonObject2.replace(keyTable, text);
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            objectMapper.writeValue(this.file, jsonObject);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
