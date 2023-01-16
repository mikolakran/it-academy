package com.pvt.validation;



import com.pvt.entity.User;
import com.pvt.exception.LoginException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Validation {

    public Validation() {
    }

    public static class Builder {
        public Builder() {
        }

        public Builder validationPassword(String pass) throws LoginException {
            Pattern pattern = Pattern.compile("(?=.*\\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[\\da-zA-Z!@#$%^&*]{8,}");
            if (pass != null) {
                Matcher matcher = pattern.matcher(pass);
                if (!matcher.find()) {
                    log.info("CheckUser.validationPassword(String pass)", new Throwable("Password is not valid"));
                    throw new LoginException("Password is not valid example: w$#$IU7caamq or *s9C#nFSNx#A");
                }
            }
            return this;
        }

        public Builder validationPassSamePass2(String pass, String pass2) throws LoginException {
            if (pass != null && pass2 != null) {
                if (!pass.equals(pass2)) {
                    log.info("CheckUser.validationPassSamePass2(String pass, String pass2)",
                            new Throwable("passwords do not match"));
                    throw new LoginException("passwords do not match");
                }
            }
            return this;
        }

        public Builder validationName(String passOrUserName) throws LoginException {
            Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z\\d-_.]{3,16}");
            if (passOrUserName != null) {
                Matcher matcher = pattern.matcher(passOrUserName);
                if (!matcher.find()) {
                    log.info("CheckUser.validationName(String passOrUserName)", new Throwable("Name is not valid"));
                    throw new LoginException("Name is not valid example: rad2fas22");
                }
            }
            return this;
        }


        public Builder validationEmail(String email) throws LoginException {
            Pattern pattern = Pattern.compile("[a-zA-Z\\d]{3,16}@[a-zA-Z\\d\\S]+\\.[a-zA-Z\\d]{2,3}");
            if (email != null) {
                Matcher matcher = pattern.matcher(email);
                if (!matcher.find()) {
                    log.info("CheckUser.validationEmail(String email)", new Throwable("Email is not valid"));
                    throw new LoginException("Email is not valid");
                }
            }
            return this;
        }


        public Builder isUpdateUserPossibly(User user, List<User> users) throws LoginException {
            for (User listUser : users) {
                if (listUser.getUserName().equals(user.getUserName()) &&
                        listUser.getId() != user.getId()) {
                    log.error("UserDAOImpl.update(User user)", new Throwable("Name exist = " + user.getUserName()));
                    throw new LoginException("name exist");
                }
                if (listUser.getEmail().equals(user.getEmail()) &&
                        listUser.getId() != user.getId()) {
                    log.error("UserDAOImpl.update(User user)", new Throwable("E-mail exist = " + user.getEmail()));
                    throw new LoginException("email exist");
                }
            }
            return this;
        }

        public void build() {
            new Validation();
        }
    }
}
