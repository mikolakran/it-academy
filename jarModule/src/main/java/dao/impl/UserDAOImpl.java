package dao.impl;


import dao.BaseDAO;
import dao.UserDAO;
import entity.Topic;
import entity.User;
import exception.LoginException;
import exception.MyException;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import validation.Validation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class UserDAOImpl extends BaseDAO<User, Long> implements UserDAO {

    public UserDAOImpl() {
        super();
        aClass = User.class;
    }

    @Override
    public User save(User user) throws MyException {
        log.trace("UserDAOImpl.save(User user) " + user);
        new Validation.Builder().validationName(user.getUserName()).validationEmail(user.getEmail()).
                validationPassword(user.getPassword()).build();
        validationSQL(user);
        return super.save(user);
    }

    @Override
    public User get(Long aLong) {
        log.trace("UserDAOImpl.get(Long aLong) " + aClass + " with id = " + aLong);
        return super.get(aLong);
    }

    @Override
    public void update(User user) throws MyException {
        new Validation.Builder().validationName(user.getUserName()).validationEmail(user.getEmail()).
                validationPassword(user.getPassword()).isUpdateUserPossibly(user, getListUsers()).build();
        log.trace("UserDAOImpl.update(User user) user = " + user);
        super.update(user);
    }

    @Override
    public void delete(Long aLong) {
        log.trace("UserDAOImpl.delete(Long aLong) aLong = " + aLong);
        super.delete(aLong);
    }

    @Override
    public User getByName(String name) {
        User user = null;
        TypedQuery<User> typedQuery = entityManager.createNamedQuery("getByUserName", User.class).
                setParameter("name", name);
        List<User> resultList = typedQuery.getResultList();
        if (resultList.size() != 0) {
            for (User user1 : resultList) {
                user = user1;
            }
        }
        log.trace("UserDAOImpl.getByName(String name) name = " + name + " in user = " + user);
        return user;
    }

    @Override
    public User getByEmail(String email) {
        User user = null;
        TypedQuery<User> typedQuery = entityManager.createNamedQuery("getByUserEmail", User.class).
                setParameter("email", email);
        List<User> resultList = typedQuery.getResultList();
        if (resultList.size() != 0) {
            for (User user1 : resultList) {
                user = user1;
            }
        }
        log.trace("UserDAOImpl.getByEmail(String email) email = " + email + " in user = " + user);
        return user;
    }

    @Override
    public List<User> getListUsers() {
        List<User> users;
        TypedQuery<User> typedQuery = entityManager.createNamedQuery("getAllUser", User.class);
        users = typedQuery.getResultList().stream().
                filter(user -> user.getRole().equals("user")).collect(Collectors.toList());
        log.trace("UserDAOImpl.getListUsers()");
        return users;
    }

    @Override
    public Set<User> getListUsersWhereIdTopic(long id) {
        TypedQuery<Topic> typedQuery = entityManager.createNamedQuery("getAllUserWhereIdTopic", Topic.class).
                setParameter("id", id);
        Topic topic = typedQuery.getSingleResult();
        log.trace("UserDAOImpl.getListUsersWhereIdTopic(long id) id = " + id);
        return topic.getUsers();
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
        log.trace("UserDAOImpl.getRole(User user) role = " + role + " in user =" + user);
        return role;
    }

    private void validationSQL(User user) throws MyException {
        if (getByName(user.getUserName()) != null) {
            log.error("UserDAOImpl.validationSQL(User user)",
                    new Throwable("user.getUserName() != null = " + user));
            throw new LoginException("name exist");
        } else {
            log.trace("UserDAOImpl.save(User user) = true");
        }
        if (getByEmail(user.getEmail()) != null) {
            log.error("UserDAOImpl.validationSQL(User user)",
                    new Throwable("user.getEmail() != null = " + user));
            throw new LoginException("email exist");
        } else {
            log.trace("UserDAOImpl.save(User user) = true");
        }
    }

}
