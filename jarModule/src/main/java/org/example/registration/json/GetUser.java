package org.example.registration.json;

import org.example.registration.baseConnection.DriverConnection;
import org.example.registration.baseConnection.PropertiesSQL;
import org.example.registration.inter.ReadingUser;
import org.example.registration.user.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetUser implements ReadingUser {
    private final User user = new User();

    public User getUserByKey(int key){
        try (PreparedStatement preparedStatement = DriverConnection.driverConnection().prepareStatement(
                PropertiesSQL.getProperties("SQL_USER_ID")+'?'
        )){
            preparedStatement.setInt(1,key);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                user.setId(Integer.parseInt(resultSet.getString("id")));
                user.setUserName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getString("role"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
    public User getUserByKeyTableName(String name) {
        try (PreparedStatement preparedStatement = DriverConnection.driverConnection().prepareStatement(
                PropertiesSQL.getProperties("SQL_USER_NAME")+'?'
        )){
            getUser(name, preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public User getUserByKeyTableId(String id) {
        try (PreparedStatement preparedStatement = DriverConnection.driverConnection().prepareStatement(
                PropertiesSQL.getProperties("SQL_USER_ID")+'?'
        )){
            getUser(id, preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    private void getUser(String name, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            user.setId(resultSet.getInt("id"));
            user.setUserName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
            user.setEmail(resultSet.getString("email"));
            user.setRole(resultSet.getString("role"));
        }
    }

    public List<User> getListUsers(){
        List<User> listUsers = new ArrayList<>();
        try (PreparedStatement preparedStatement = DriverConnection.driverConnection().prepareStatement(
                PropertiesSQL.getProperties("SQL_USERS")
        )){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                User user = new User();
                user.setId(Integer.parseInt(resultSet.getString("id")));
                user.setUserName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getString("role"));
                if (user.getRole().equals("user")) {
                    listUsers.add(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listUsers;
    }


}
