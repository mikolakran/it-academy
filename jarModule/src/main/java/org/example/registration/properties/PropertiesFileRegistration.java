package org.example.registration.properties;

import java.io.*;
import java.util.Properties;

public class PropertiesFileRegistration {
    private static final Properties properties = new Properties();

    public static File getProperties(){
        File file ;
        try {
            FileInputStream fileInputStream = new FileInputStream("C:/Users/mikol/it-academy/jarModule/src/main/resources/my.properties");
            properties.load(fileInputStream);
             file = new File(properties.getProperty("FILE_PATH"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }
}
