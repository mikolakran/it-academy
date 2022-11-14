package validation;

import exception.LoginException;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class CheckUser implements ValidationAuth {
    @Override
    public void validationPassword(String pass) throws LoginException {
        Pattern pattern = Pattern.compile("(?=.*\\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[\\da-zA-Z!@#$%^&*]{8,}");
        if (pass != null) {
            Matcher matcher = pattern.matcher(pass);
            if (!matcher.find()) {
                log.trace("Password is not valid", new Throwable("Password is not valid"));
                throw new LoginException("Password is not valid example: w$#$IU7caamq or *s9C#nFSNx#A");
            }
        }
    }

    public void validationPassSamePass2(String pass, String pass2) throws LoginException {
        if (pass != null && pass2 != null) {
            if (!pass.equals(pass2)) {
                log.trace("passwords do not match", new Throwable("passwords do not match"));
                throw new LoginException("passwords do not match");
            }
        }
    }

    public void validationName(String passOrUserName) throws LoginException {
        Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z\\d-_.]{3,16}");
        if (passOrUserName != null) {
            Matcher matcher = pattern.matcher(passOrUserName);
            if (!matcher.find()) {
                log.trace("Name is not valid", new Throwable("Name is not valid"));
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
                log.trace("Email is not valid", new Throwable("Email is not valid"));
                throw new LoginException("Email is not valid");
            }
        }
    }

    @Override
    public boolean validationSQL(Exception e) throws LoginException {
        Pattern pattern = Pattern.compile("(')[a-zA-Z\\d]{3,16}@[a-zA-Z\\d\\S]+\\.[a-zA-Z\\d]{2,3}(')");
        Matcher matcher = pattern.matcher(e.getMessage());
        if (matcher.find()) {
            log.trace("E-mail exist", new Throwable(e.getMessage()));
            throw new LoginException("E-mail exist");
        }
        Pattern pattern2 = Pattern.compile("(')[a-zA-Z][a-zA-Z\\d-_.]{3,16}(')");
        Matcher matcher2 = pattern2.matcher(e.getMessage());
        if (matcher2.find()) {
            log.trace("Name exist", new Throwable(e.getMessage()));
            throw new LoginException("Name exist");
        }
        return true;
    }
}
