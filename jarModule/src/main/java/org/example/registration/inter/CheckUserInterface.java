package org.example.registration.inter;


import org.example.registration.user.User;

import java.io.File;
import java.util.List;

public interface CheckUserInterface {
    boolean IsExistUser(User user, List<String> id, int count, File file);
    void setExistUser(boolean exist);
    String isValidationPasswordOrUserName(String pass);
    String isValidationEmail(String pass);
}
