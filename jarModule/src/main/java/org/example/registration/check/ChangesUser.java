package org.example.registration.check;


import org.example.registration.baseConnection.DriverConnection;
import org.example.registration.baseConnection.PropertiesSQL;
import org.example.registration.inter.ChangesUserInterface;


import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChangesUser implements ChangesUserInterface {

    public void deleteUserByKey(int key){
        try (PreparedStatement preparedStatement = DriverConnection.driverConnection().prepareStatement(
                PropertiesSQL.getProperties("SQL_USER_DELETE")+'?')){
            preparedStatement.setInt(1,key);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void changesBySelectTable(int key, String keyTable, String text){
        try (PreparedStatement preparedStatement = DriverConnection.driverConnection().prepareStatement(
                "UPDATE users SET "+keyTable+"=? WHERE id=?;")){
            preparedStatement.setString(1,text);
            preparedStatement.setInt(2,key);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
