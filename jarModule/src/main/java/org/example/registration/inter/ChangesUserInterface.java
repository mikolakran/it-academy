package org.example.registration.inter;

import java.io.File;

public interface ChangesUserInterface {
    void changesUserBySelectionTable(String key, String key_table, String table, File file);
    void deleteUserByKey(String key, File file);
}
