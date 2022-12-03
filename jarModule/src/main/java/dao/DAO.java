package dao;

import exception.CatchingCauseException;
import exception.LoginException;

public interface DAO<T,K> {
    void save(T t) throws LoginException, CatchingCauseException;

    T get(K k) throws CatchingCauseException;

    void update(T t) throws LoginException, CatchingCauseException;

    void delete(K k) throws CatchingCauseException;
}
