package dao;

import exception.MyException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Slf4j
public class BaseDAO<T, K> implements DAO<T, K> {

    protected Class<T> aClass;


    @PersistenceContext
    @Getter
    protected EntityManager entityManager;


    @Transactional
    @Override
    public void save(T t) throws MyException {
        entityManager.persist(t);
    }

    @Override
    public T get(K k) {
        return entityManager.find(aClass, k);
    }


    @Transactional
    @Override
    public void update(T t) throws MyException {
        entityManager.merge(t);
    }

    @Transactional
    @Override
    public void delete(K k) {
        entityManager.remove(entityManager.find(aClass, k));
    }
}
