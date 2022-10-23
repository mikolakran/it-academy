package validation;

import exception.LoginException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CheckUser implements ValidationAuth {
    @Override
    public void validationPassword(String pass) throws LoginException {
        Pattern pattern = Pattern.compile("(?=.*\\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[\\da-zA-Z!@#$%^&*]{8,}");
        if (pass != null) {
            Matcher matcher = pattern.matcher(pass);
            if (!matcher.find()) {
                throw new LoginException("Password is not valid example: w$#$IU7caamq or *s9C#nFSNx#A");
            }
        }
    }

    public void validationPassSamePass2(String pass, String pass2) throws LoginException {
        if (pass != null && pass2 != null) {
            if (!pass.equals(pass2)) {
                throw new LoginException("passwords do not match");
            }
        }
    }

    public void validationName(String passOrUserName) throws LoginException {
        Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z\\d-_.]{3,16}");
        if (passOrUserName != null) {
            Matcher matcher = pattern.matcher(passOrUserName);
            if (!matcher.find()) {
                throw new LoginException("Name is not valid example: rad2fas22");
            }
        }
    }

    @Override
    public void validationEmail(String email) throws LoginException {
        Pattern pattern = Pattern.compile("[a-zA-Z\\d]{3,16}@[a-zA-Z\\d\\S]+\\.[a-zA-Z\\d]{2,3}");
        if (email != null) {
            Matcher matcher = pattern.matcher(email);
            if (!matcher.find()) {
                throw new LoginException("Email is valid");
            }
        }
    }

    @Override
    public void validationSQL(Exception e) throws LoginException {
        String patternEmail = "(user.email_UNIQUE)";
        String patternName = "(user.name_UNIQUE)";
        Pattern pattern = Pattern.compile(patternEmail);
        Matcher matcher = pattern.matcher(e.getMessage());
        if (matcher.find()) {
            throw new LoginException("E-mail exist");
        }
        Pattern pattern2 = Pattern.compile(patternName);
        Matcher matcher2 = pattern2.matcher(e.getMessage());
        if (matcher2.find()) {
            throw new LoginException("Name exist");
        }
    }
}
