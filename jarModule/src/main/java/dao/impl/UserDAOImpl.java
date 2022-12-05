package dao.impl;


import dao.BaseDAO;
import dao.UserDAO;
import entity.User;
import exception.CatchingCauseException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;


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

    @Override
    public User getByName(String name) throws CatchingCauseException {
        User user = new User();
        try {
            TypedQuery<User> typedQuery = entityManager.createNamedQuery("getByUserName", User.class).
                    setParameter("name", name);
            user = typedQuery.getSingleResult();
        } catch (NoResultException ignored) {
        } catch (PersistenceException e) {
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

}
