package org.example.registration.check;

import org.example.registration.inter.exception.LoginException;
import org.example.registration.inter.CheckUserInterface;
import org.example.registration.inter.ReadingUser;
import org.example.registration.json.GetUser;
import org.example.registration.user.User;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CheckUser implements CheckUserInterface {
    private boolean exist = false;


    /*Check User на совпадение*/
    @Override
    public boolean IsExistUser(User user, File file) throws LoginException {
        ReadingUser readingJson = new GetUser(file);
        if (readingJson.getAllUser() != null && !exist) {
            JSONObject jsonObject2 = readingJson.getAllUser();
            long idMax = readingJson.getIdMax(jsonObject2);
            JSONObject jsonObject3 = (JSONObject) jsonObject2.get("people");
            if (jsonObject3 != null) {
                JSONObject jsonObject4;
                for (int i = 1; i <= idMax; i++) {
                    String idKey = String.valueOf(i);
                    jsonObject4 = (JSONObject) jsonObject3.get(idKey);
                    if (jsonObject4 != null) {
                        if (user.getUserName() != null) {
                            if (user.getUserName().equals(jsonObject4.get("userName"))) {
                                exist = true;
                                throw new LoginException("User name exist");
                            }
                        }
                        if (user.getPassword() != null) {
                            if (user.getPassword().equals(jsonObject4.get("password"))) {
                                exist = true;
                                throw new LoginException("User password  exist");
                            }
                        }
                        if (user.getEmail() != null) {
                            if (user.getEmail().equals(jsonObject4.get("email"))) {
                                exist = true;
                                throw new LoginException("User email  exist");
                            }
                        }
                    }
                }
            }
        }
        return exist;
    }

    @Override
    public void IsExistUserUserName(User user, File file) throws LoginException {
        ReadingUser readingJson = new GetUser(file);
        if (readingJson.getAllUser() != null && !exist) {
            JSONObject jsonObject2 = readingJson.getAllUser();
            long idMax = readingJson.getIdMax(jsonObject2);
            JSONObject jsonObject3 = (JSONObject) jsonObject2.get("people");
            if (jsonObject3 != null) {
                JSONObject jsonObject4;
                for (int i = 1; i <= idMax; i++) {
                    String idKey = String.valueOf(i);
                    jsonObject4 = (JSONObject) jsonObject3.get(idKey);
                    if (jsonObject4 != null) {
                        if (user.getUserName() != null) {
                            if (user.getUserName().equals(jsonObject4.get("userName"))) {
                                exist = true;
                                throw new LoginException("User name exist");
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void IsExistUserPassword(User user, File file) throws LoginException {
        ReadingUser readingJson = new GetUser(file);
        if (readingJson.getAllUser() != null && !exist) {
            JSONObject jsonObject2 = readingJson.getAllUser();
            long idMax = readingJson.getIdMax(jsonObject2);
            JSONObject jsonObject3 = (JSONObject) jsonObject2.get("people");
            if (jsonObject3 != null) {
                JSONObject jsonObject4;
                for (int i = 1; i <= idMax; i++) {
                    String idKey = String.valueOf(i);
                    jsonObject4 = (JSONObject) jsonObject3.get(idKey);
                    if (jsonObject4 != null) {
                        if (user.getPassword() != null) {
                            if (user.getPassword().equals(jsonObject4.get("password"))) {
                                exist = true;
                                throw new LoginException("User password  exist");
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void IsExistUserEmail(User user, File file) throws LoginException {
        ReadingUser readingJson = new GetUser(file);
        if (readingJson.getAllUser() != null && !exist) {
            JSONObject jsonObject2 = readingJson.getAllUser();
            long idMax = readingJson.getIdMax(jsonObject2);
            JSONObject jsonObject3 = (JSONObject) jsonObject2.get("people");
            if (jsonObject3 != null) {
                JSONObject jsonObject4;
                for (int i = 1; i <= idMax; i++) {
                    String idKey = String.valueOf(i);
                    jsonObject4 = (JSONObject) jsonObject3.get(idKey);
                    if (jsonObject4 != null) {
                        if (user.getEmail() != null) {
                            if (user.getEmail().equals(jsonObject4.get("email"))) {
                                exist = true;
                                throw new LoginException("User email  exist");
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void setExistUser(boolean exist) {
        this.exist = exist;
    }


    @Override
    public void isValidationPassword(String passOrUserName) throws LoginException {
        Pattern pattern = Pattern.compile("(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{8,}");
        if (passOrUserName != null) {
            Matcher matcher = pattern.matcher(passOrUserName);
            if (matcher.find()) {
                passOrUserName = matcher.group();
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
        Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9-_\\.]{3,16}");
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
