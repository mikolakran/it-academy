package dao;

import entity.User;
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
public class BaseDAO<T, K> implements DAO<T, K> {
    private final ValidationAuth checkUser = new CheckUser();
    protected Class<T> aClass;

    @PersistenceContext
    @Getter
    protected EntityManager entityManager;

    @Transactional(rollbackOn = {LoginException.class,CatchingCauseException.class})
     @Override
    public void save(T t) throws LoginException, CatchingCauseException {
        try {
            entityManager.persist(t);
        } catch (PersistenceException e) {
            try {
                CatchingCauseException.findPathToCause(e);
            } catch (CatchingCauseException ex) {
                if (checkUser.validationSQL(ex)) {
                    log.error("BaseDAO.save", ex);
                    throw new CatchingCauseException(e);
                }
            }
        }
    }

    @Override
    public T get(K k) throws CatchingCauseException {
        T t;
        try {
            t = entityManager.find(aClass, k);
        } catch (PersistenceException e) {
            log.error("BaseDAO.get", e);
            throw new CatchingCauseException(e);
        }
        return t;
    }


    @Transactional(rollbackOn = {LoginException.class,CatchingCauseException.class})
    @Override
    public void update(T t) throws LoginException, CatchingCauseException {
        try {
            entityManager.merge(t);
        } catch (PersistenceException e) {
            try {
                CatchingCauseException.findPathToCause(e);
            } catch (CatchingCauseException ex) {
                if (checkUser.validationSQL(ex)) {
                    log.error("BaseDAO.update", ex);
                    throw new CatchingCauseException(e);
                }
            }
        }
    }

    @Transactional(rollbackOn = {CatchingCauseException.class})
    @Override
    public void delete(K k) throws CatchingCauseException {
        try {
            entityManager.remove(entityManager.find(User.class, k));
        } catch (PersistenceException | IllegalArgumentException e) {
            log.error("BaseDAO.delete", e);
            throw new CatchingCauseException(e);
        }
    }
}
