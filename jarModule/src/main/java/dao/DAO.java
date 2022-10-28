package dao;


import exception.LoginException;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

public interface DAO <T>{
    void save(T t) throws LoginException, SQLException, PropertyVetoException;
    T get(long id) throws PropertyVetoException, SQLException, LoginException;
    void update(long key, String keyTable, String text) throws LoginException, SQLException, PropertyVetoException;
    void delete(long id) throws LoginException;
}
