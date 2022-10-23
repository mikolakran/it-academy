package dao.impl;


import connector.DataSourceConnectors;
import dao.UserDAO;
import entity.User;
import exception.LoginException;
import properties.PropertiesSQL;
import validation.CheckUser;
import validation.ValidationAuth;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDAOImpl implements UserDAO {
    private final ValidationAuth checkUser = new CheckUser();
    @Override
    public User save(User user) throws LoginException {
        if (user.getUserName().equals(user.getPassword())) {
            throw new LoginException("userName and password same");
        }
            String sql = PropertiesSQL.getProperties("SQL_USER_INSERT_USER") + "?,?, ?, ?,?);";
            PreparedStatement preparedStatement;
            try (Connection connection = DataSourceConnectors.getInstance().getConnection()) {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, 0);
                preparedStatement.setString(2, user.getUserName());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getRole());
                preparedStatement.setString(5, user.getPassword());
                preparedStatement.executeUpdate();
            } catch (SQLException  e) {
                checkUser.validationSQL(e);
            } catch (PropertyVetoException e) {
                throw new RuntimeException(e);
            }
        return null;
    }

    @Override
    public User get(long id) {
        User user = new User();
        PreparedStatement preparedStatement;
        String sql = PropertiesSQL.getProperties("SQL_USER_ID")+"?";
        try (Connection connection = DataSourceConnectors.getInstance().getConnection()){
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, (int) id);
            setResult(user, preparedStatement);
        } catch (SQLException | PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        return user;
    }


    @Override
    public User getByName(String name) {
        User user = new User();
        PreparedStatement preparedStatement;
        String sql = PropertiesSQL.getProperties("SQL_USER_NAME")+"?";
        try (Connection connection = DataSourceConnectors.getInstance().getConnection()){
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            setResult(user, preparedStatement);
        } catch (SQLException | PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public List<User> getListUsers() {
        List<User> listUsers = new ArrayList<>();
        PreparedStatement preparedStatement;
        String sql = PropertiesSQL.getProperties("SQL_USERS");
        try (Connection connection = DataSourceConnectors.getInstance().getConnection()){
            preparedStatement = connection.prepareStatement(sql);
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
        } catch (SQLException | PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        return listUsers;
    }

    @Override
    public String getRole(User user) {
        String role = null;
        if (user!=null) {
            if (user.getEmail().equals("mikolakran@gmail.com")) {
                role = "admin";
            } else {
                role = "user";
            }
        }
        return role;
    }

    private void setResult(User user, PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            user.setId(Integer.parseInt(resultSet.getString("id")));
            user.setUserName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
            user.setEmail(resultSet.getString("email"));
            user.setRole(resultSet.getString("role"));
        }
    }

    @Override
    public void update(long key, String keyTable, String text) throws LoginException {
        PreparedStatement preparedStatement;
        String sql = "UPDATE user SET "+keyTable+"=? WHERE id=?;";
        try (Connection connection = DataSourceConnectors.getInstance().getConnection()){
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,text);
            preparedStatement.setInt(2, (int) key);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            checkUser.validationSQL(e);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        PreparedStatement preparedStatement;
        String sql = PropertiesSQL.getProperties("SQL_USER_DELETE")+"?";
        try (Connection connection = DataSourceConnectors.getInstance().getConnection()){
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, (int) id);
            preparedStatement.executeUpdate();
        } catch (SQLException | PropertyVetoException e) {
            throw new RuntimeException(e);
        }
    }
}
