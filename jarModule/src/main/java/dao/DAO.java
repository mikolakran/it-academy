package dao;


import exception.LoginException;

public interface DAO <T>{
    T save(T t) throws LoginException;
    T get(long id);
    void update(long key, String keyTable, String text) throws LoginException;
    void delete(long id);
}
