package org.example.registration.json;

import org.example.registration.baseConnection.DriverConnection;
import org.example.registration.baseConnection.PropertiesSQL;
import org.example.registration.check.CheckUser;
import org.example.registration.inter.exception.LoginException;
import org.example.registration.inter.CheckUserInterface;
import org.example.registration.inter.WriteFileUser;
import org.example.registration.user.User;


import java.sql.PreparedStatement;
import java.sql.SQLException;


public class RecordUser implements WriteFileUser {

    private final CheckUserInterface checkUser = new CheckUser();

    public void writeUser(User user) throws LoginException {
        if (user.getUserName().equals(user.getPassword())) {
            checkUser.setExistUser(true);
            throw new LoginException("userName and password same");
        }
        if (!checkUser.isExistUser(user)) {
            String sql = PropertiesSQL.getProperties("SQL_USER_INSERT_USER") + "?,?, ?, ?,?);";
            try (PreparedStatement preparedStatement = DriverConnection.driverConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, 0);
                preparedStatement.setString(2, user.getUserName());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getRole());
                preparedStatement.setString(5, user.getPassword());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            checkUser.setExistUser(false);
        }
    }

}
