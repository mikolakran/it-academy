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
    private boolean exist;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public File file;
    public File file2;

    public ChangesUser(File file, File file2) {
        this.file = file;
        this.file2 = file2;
    }

    @Override
    public void changesUserBySelectionTable(String key, String key_table, String table, File file) { //изменяет user по выбор таблицы email,userName,password
        ReadingUser getDateBaseUser = new GetUser(file);
        String text = getDateBaseUser.getUserByKeyTable(key, key_table);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file2));
            String s;
            do {
                s = bufferedReader.readLine();
                if (s.contains(text)) {
                    if (!isExist(table)) {
                        bufferedWriter.write("      " + "\"" + key_table + "\"" + "" + ":" + "\"" + table + "\"" + ",");
                    } else {
                        bufferedWriter.write("      " + "\"" + key_table + "\"" + "" + ":" + "\"" + text + "\"" + ",");
                        System.out.println(ChangesUser.class + "= " + "File string exist");
                    }
                } else {
                    bufferedWriter.write(s);
                }
                bufferedWriter.newLine();
            } while (bufferedReader.ready());
            bufferedReader.close();
            bufferedWriter.flush();
            bufferedWriter.close();
            file.delete();
            file2.renameTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void deleteUserByKey(String key, File file){
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject ;
        try {
            jsonObject = (JSONObject) jsonParser.parse(new FileReader(this.file));
            JSONObject jsonObject1 = (JSONObject) jsonObject.get("people");
            JSONObject jsonObject2 = (JSONObject) jsonObject1.remove(key);
            jsonObject.remove(jsonObject2);
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            objectMapper.writeValue(this.file,jsonObject);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isExist(String table) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String s;
            do {
                s = bufferedReader.readLine();
                if (s.contains(table)) {
                    exist = true;
                }
            } while (bufferedReader.ready() && !exist);
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return exist;
    }
}
