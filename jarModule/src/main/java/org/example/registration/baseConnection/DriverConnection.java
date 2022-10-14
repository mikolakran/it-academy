package org.example.registration.baseConnection;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverConnection {
    // radfast *s9C#nFSNx#A mikolakran@gmail.com admin

// CREATE DATABASE `registration`;

/*CREATE TABLE users (
            id INT NOT NULL AUTO_INCREMENT,
            name VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (id));*/
    public static Connection driverConnection() throws SQLException {
        Driver driver = new com.mysql.cj.jdbc.Driver();
        DriverManager.registerDriver(driver);
        String DATABASE_URL = "jdbc:mysql://localhost:3306/it-academy";
        return DriverManager.getConnection(DATABASE_URL, "root", "");
    }

}
