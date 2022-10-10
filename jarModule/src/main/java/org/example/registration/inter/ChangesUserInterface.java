package org.example.registration.inter;

import org.example.registration.inter.exception.LoginException;

import java.io.File;

public interface ChangesUserInterface {
    void changesUserBySelectionTable(String key, String key_table, String table, File file) throws LoginException;
    void deleteUserByKey(String key, File file);
}
