package org.example.registration.baseConnection;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverConnection {
    public static Connection driverConnection() throws SQLException {
        Driver driver = new com.mysql.cj.jdbc.Driver();
        DriverManager.registerDriver(driver);
        String DATABASE_URL = "jdbc:mysql://localhost:3306/it-academy";
        return DriverManager.getConnection(DATABASE_URL, "root", "");
    }

}
