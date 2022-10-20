package org.example.registration.inter;

public interface ChangesUserInterface {
    void deleteUserByKey(int key);
    void changesBySelectTable(int key, String keyTable, String text);
}
