package dao;

import exception.CatchingCauseException;
import exception.LoginException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import validation.CheckUser;
import validation.ValidationAuth;


@Repository
@Slf4j
public class BaseDAO<T,K> implements DAO<T,K> {
    private final ValidationAuth checkUser = new CheckUser();
    protected Class<T> aClass;

    @PersistenceContext
    @Getter
    protected EntityManager entityManager;

    @Transactional
    @Override
    public void save(T t) throws LoginException, CatchingCauseException {
        try {
            entityManager.persist(t);
        } catch (PersistenceException e) {
            try {
                CatchingCauseException.findPathToCause(e);
            } catch (CatchingCauseException ex) {
                if (checkUser.validationSQL(ex)) {
                    log.error("UserDAOImpl.save", ex);
                    throw new CatchingCauseException(e);
                }
            }
        }
    }

    @Override
    public T get(K k) throws CatchingCauseException {
        return null;
    }

    @Override
    public void update(T t) throws LoginException, CatchingCauseException {

    }

    @Override
    public void delete(K k) throws CatchingCauseException {

    }
}
