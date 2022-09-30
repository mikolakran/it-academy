package org.example.registration.inter;

import java.io.File;

public interface ChangesUser_inter {
    void changes_user(String key, String key_table, String table, File file);
    void changes_all_user(String key,File file);
}
