package org.web.facades;

import dao.UserDAO;
import entity.User;
import exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web.forms.TopicForm;
import org.web.forms.UserForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class UserFacade {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TopicFacade topicFacade;

    public UserForm save(UserForm userForm) throws MyException {
        User user = new User();
        buildUser(user, userForm);
        User resultSave = userDAO.save(user);
        return new UserForm(resultSave);
    }

    public void update(UserForm userForm) throws MyException {
        User user = new User();
        buildUser(user, userForm);
        userDAO.update(user);
    }

    public UserForm get(Long id) {
        User user = new User();
        UserForm userForm = new UserForm();
        userForm.setId(id);
        buildUser(user, userForm);
        return new UserForm(userDAO.get(user.getId()));
    }

    public void delete(Long idKey) {
        User user = new User();
        UserForm userForm = get(idKey);
        Set<TopicForm> listTopic = topicFacade.getListTopic(userForm.getId());
        if (listTopic.size()!=0) {
            listTopic.forEach(topicForm -> topicFacade.deleteUserAndPost(userForm.getId(), topicForm.getId()));
        }
        buildUser(user, userForm);
        userDAO.delete(user.getId());
    }

    public UserForm getByName(String name) {
        User user = new User();
        UserForm userForm = new UserForm();
        userForm.setUserName(name);
        buildUser(user, userForm);
        User resultByName = userDAO.getByName(user.getUserName());
        return new UserForm(resultByName);
    }

    public List<UserForm> getListUsers() {
        List<UserForm> userFormList = new ArrayList<>();
        userDAO.getListUsers().forEach(user1 -> userFormList.add(new UserForm(user1)));
        return userFormList;
    }

    public void getRole(UserForm userForm) {
        User user = new User();
        buildUser(user, userForm);
        userForm.setRole(userDAO.getRole(user));
    }

    private void buildUser(User user, UserForm userForm) {
        user.setId(userForm.getId());
        user.setUserName(userForm.getUserName());
        user.setPassword(userForm.getPassword());
        user.setEmail(userForm.getEmail());
        user.setRole(userForm.getRole());
        user.setImage(userForm.getPhoto());
    }
}
