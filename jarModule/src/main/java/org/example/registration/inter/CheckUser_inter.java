package org.example.registration.inter;


import org.example.registration.user.User;

import java.io.File;
import java.util.List;

public interface CheckUser_inter {
    boolean exist_user(User user, List<String> id, int count, File file);
    void setExist(boolean exist);
}
