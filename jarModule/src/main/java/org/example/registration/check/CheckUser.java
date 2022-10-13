package org.example.registration.check;

import org.example.registration.baseConnection.DriverConnection;
import org.example.registration.baseConnection.PropertiesSQL;
import org.example.registration.inter.exception.LoginException;
import org.example.registration.inter.CheckUserInterface;
import org.example.registration.user.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CheckUser implements CheckUserInterface {
    private boolean exist = false;


    /*Check User на совпадение*/
    public boolean isExistUser(User user) throws LoginException{
        isExistUserName(user);
        isExistUserEmail(user);
        return exist;
    }

    public void isExistUserName(User user) throws  LoginException {
        try (PreparedStatement preparedStatement = DriverConnection.driverConnection().prepareStatement(
                PropertiesSQL.getProperties("SQL_USER_NAME")+'?')){
            preparedStatement.setString(1,user.getUserName());
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()&& !exist){
                exist = true;
                throw new LoginException("User name exist");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void isExistUserEmail(User user) throws LoginException {

        try (PreparedStatement preparedStatement = DriverConnection.driverConnection().prepareStatement(
                PropertiesSQL.getProperties("SQL_USER_EMAIL")+'?')){
            preparedStatement.setString(1,user.getEmail());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                exist = true;
                throw new LoginException("User email exist");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setExistUser(boolean exist) {
        this.exist = exist;
    }


    @Override
    public void isValidationPassword(String pass) throws LoginException {
        Pattern pattern = Pattern.compile("(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{8,}");
        if (pass != null) {
            Matcher matcher = pattern.matcher(pass);
            if (matcher.find()) {
                pass = matcher.group();
            } else {
                throw new LoginException("Password is not valid example: w$#$IU7caamq or *s9C#nFSNx#A");
            }
        }
    }

    public void isPassAndPass(String pass, String pass2) throws LoginException {
        if (pass != null && pass2 != null) {
            if (!pass.equals(pass2)) {
                throw new LoginException("passwords do not match");
            }
        }
    }

    public void isValidationUserName(String passOrUserName) throws LoginException {
        Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9-_.]{3,16}");
        if (passOrUserName != null) {
            Matcher matcher = pattern.matcher(passOrUserName);
            if (matcher.find()) {
                passOrUserName = matcher.group();
            } else {
                throw new LoginException("Name is not valid example: rad2fas22");
            }
        }
    }

    @Override
    public void isValidationEmail(String email) throws LoginException {
        Pattern pattern = Pattern.compile("[a-zA-Z\\d]{3,16}@[a-zA-Z\\d\\S]+\\.[a-zA-Z\\d]{2,3}");
        if (email != null) {
            Matcher matcher = pattern.matcher(email);
            if (matcher.find()) {
                email = matcher.group();
            } else {
                throw new LoginException("Email is valid");
            }
        }
    }
}
