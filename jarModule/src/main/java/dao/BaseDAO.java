package dao;

import exception.CatchingCauseException;
import exception.LoginException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDAO<T,K> implements DAO<T> {

    protected Class<T> aClass;

    @PersistenceContext
    @Getter
    protected EntityManager entityManager;

    @Transactional
    @Override
    public void save(T t) throws LoginException, CatchingCauseException {
        entityManager.persist(t);
    }

    @Override
    public T get(long id) throws CatchingCauseException {
        return null;
    }

    @Override
    public void update(T t) throws LoginException, CatchingCauseException {

    }

    @Override
    public void delete(long id) throws CatchingCauseException {

    }
}
