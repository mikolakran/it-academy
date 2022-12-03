package dao.impl;


import dao.BaseDAO;
import dao.UserDAO;
import entity.User;
import exception.CatchingCauseException;
import exception.LoginException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import validation.CheckUser;
import validation.ValidationAuth;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class UserDAOImpl extends BaseDAO<User,Long> implements UserDAO {

    public UserDAOImpl() {
        super();
        aClass = User.class;
    }

    private final ValidationAuth checkUser = new CheckUser();

    /*@Override
    public void save(User user) throws LoginException, CatchingCauseException {
        try {
            init();
            entityManager.persist(user);
        } catch (PersistenceException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            try {
                CatchingCauseException.findPathToCause(e);
            } catch (CatchingCauseException ex) {
                if (checkUser.validationSQL(ex)) {
                    log.error("UserDAOImpl.save", ex);
                    throw new CatchingCauseException(e);
                }
            }
        } finally {
            close();
        }
    }*/


    @Override
    public User get(Long id) throws CatchingCauseException {
        User user;
        try {
            user = entityManager.find(User.class, id);
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            log.error("UserDAOImpl.get", e);
            throw new CatchingCauseException(e);
        }
        return user;
    }

    @Override
    public User getByName(String name) throws CatchingCauseException {
        User user = new User();
        try {
            TypedQuery<User> typedQuery = entityManager.createNamedQuery("getByUserName", User.class).
                    setParameter("name", name);
            user = typedQuery.getSingleResult();
        } catch (NoResultException ignored) {
        } catch (PersistenceException e) {
            System.out.println("error " + Arrays.toString(e.getStackTrace()));
            entityManager.getTransaction().rollback();
            log.error("UserDAOImpl.getByName", e);
            throw new CatchingCauseException(e);
        }
        return user;
    }

    @Override
    public List<User> getListUsers() throws CatchingCauseException {
        List<User> users;
        try {
            TypedQuery<User> typedQuery = entityManager.createNamedQuery("getAllUser", User.class);
            users = typedQuery.getResultList().stream().
                    filter(user -> user.getRole().equals("user")).collect(Collectors.toList());

        } catch (PersistenceException | IllegalStateException e) {
            entityManager.getTransaction().rollback();
            log.error("UserDAOImpl.getListUsers", e);
            throw new CatchingCauseException(e);
        }
        return users;
    }

    @Override
    public User getRoleData(String idAdmin) throws CatchingCauseException {
        User user;
        try {
            TypedQuery<User> typedQuery = entityManager.createNamedQuery("getAdmin", User.class).
                    setParameter("role", idAdmin);
            user = typedQuery.getSingleResult();
        } catch (PersistenceException e) {
            System.out.println("error " + Arrays.toString(e.getStackTrace()));
            entityManager.getTransaction().rollback();
            log.error("UserDAOImpl.getRoleData", e);
            throw new CatchingCauseException(e);
        }
        return user;
    }

    @Override
    public String getRole(User user) {
        String role = null;
        if (user != null) {
            if (user.getEmail().equals("mikolakran@gmail.com")) {
                role = "admin";
            } else {
                role = "user";
            }
        }
        return role;
    }

    @Override
    public void update(User user) throws LoginException, CatchingCauseException {
        try {
            entityManager.merge(user);
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            try {
                CatchingCauseException.findPathToCause(e);
            } catch (CatchingCauseException ex) {
                if (checkUser.validationSQL(ex)) {
                    log.error("UserDAOImpl.update", ex);
                    throw new CatchingCauseException(e);
                }
            }
        }
    }

    @Override
    public void delete(Long id) throws CatchingCauseException {
        try {
            entityManager.remove(entityManager.find(User.class, id));
        } catch (PersistenceException | IllegalArgumentException e) {
            entityManager.getTransaction().rollback();
            log.error("UserDAOImpl.delete", e);
            throw new CatchingCauseException(e);
        }
    }
}
