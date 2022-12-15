package org.web.facades;

import dao.UserDAO;
import entity.User;
import exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web.forms.UserForm;

@Component
public class UserFacade {

    @Autowired
    private UserDAO userDAO;


    public UserForm save(UserForm userForm) throws MyException {
        User user = new User();
        buildUser(user,userForm);
        User resultSave = userDAO.save(user);
        return new UserForm(resultSave);
    }

    public String getRole(UserForm userForm) {
        User user = new User();
        buildUser(user,userForm);
        userForm.setRole(userDAO.getRole(user));
        return userForm.getRole();
    }

    private void buildUser(User user,UserForm userForm){
        user.setUserName(userForm.getUserName());
        user.setPassword(userForm.getPassword());
        user.setEmail(userForm.getEmail());
        user.setRole(userForm.getRole());
    }
}
