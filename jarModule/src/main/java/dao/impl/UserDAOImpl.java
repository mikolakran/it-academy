package dao.impl;

import dao.UserDAO;
import entity.User;
import exception.LoginException;
import exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import repository.UserJpaRepository;
import validation.Validation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class UserDAOImpl implements UserDAO {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) throws MyException {
        log.trace("UserDAOImpl.save(User user) " + user);
        new Validation.Builder().validationName(user.getUserName()).validationEmail(user.getEmail()).
                validationPassword(user.getPassword()).build();
        validationSQL(user);
        return userJpaRepository.save(user);
    }

    @Override
    public User get(Long aLong) {
        log.trace("UserDAOImpl.get(Long aLong)  with id = " + aLong);
        return userJpaRepository.findById(aLong).orElse(null);
    }

    @Override
    public void update(User user) throws MyException {
        new Validation.Builder().validationName(user.getUserName()).validationEmail(user.getEmail()).
                validationPassword(user.getPassword()).isUpdateUserPossibly(user, getListUsers()).build();
        log.trace("UserDAOImpl.update(User user) user = " + user);
        userJpaRepository.saveAndFlush(user);
    }

    @Override
    public void delete(Long aLong) {
        log.trace("UserDAOImpl.delete(Long aLong) aLong = " + aLong);
        userJpaRepository.deleteById(aLong);
    }

    @Override
    public User getByName(String name) {
        log.trace("UserDAOImpl.getByName(String name) name = " + name);
        return userJpaRepository.findByUserName(name);
    }

    @Override
    public User getByEmail(String email) {
        log.trace("UserDAOImpl.getByEmail(String email) email = " + email);
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public List<User> getListUsers() {
        List<User> resultAllUser = userJpaRepository.findAll();
        List<User> resultUserRole = resultAllUser.stream().filter(user -> user.getRole().equals("user")).collect(Collectors.toList());
        log.trace("UserDAOImpl.getListUsers()");
        return resultUserRole;
    }

    @Override
    public Set<User> getListUsersWhereIdTopic(long id) {
        log.trace("UserDAOImpl.getListUsersWhereIdTopic(long id) id = " + id);
        return userJpaRepository.getListUsersWhereIdTopic(id).getUsers();
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
