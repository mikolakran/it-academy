package org.example.registration.baseConnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesSQL {
    private static final Properties properties = new Properties();
// CREATE DATABASE `registration`;

    /*CREATE TABLE users (
                id INT NOT NULL AUTO_INCREMENT,
                name VARCHAR(50) NOT NULL,
        password VARCHAR(50) NOT NULL,
        email VARCHAR(50) NOT NULL,
        role VARCHAR(50) NOT NULL,
        PRIMARY KEY (id));*/
    public static String getProperties(String SQL){
        String sql;
        try {
            FileInputStream fileInputStream = new FileInputStream("C:/Users/mikol/work/work-it-academy/jarModule/src/main/resources/sql.properties");
            properties.load(fileInputStream);
            sql = String.valueOf(new File(properties.getProperty(SQL)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sql;
    }
}
