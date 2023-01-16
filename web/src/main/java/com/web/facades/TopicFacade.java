package com.web.facades;


import com.pvt.dao.TopicDAO;
import com.pvt.entity.Topic;
import com.pvt.entity.User;
import com.pvt.exception.MyException;
import com.web.forms.TopicForm;
import com.web.forms.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TopicFacade {
    @Autowired
    private TopicDAO topicDAO;

    public void save(TopicForm topicForm) throws MyException {
        Topic topic = new Topic();
        buildTopic(topic, topicForm);
        topicDAO.save(topic);
    }

    public TopicForm get(Long id) {
        Topic topic = topicDAO.get(id);
        return new TopicForm(topic);
    }

    public void update(TopicForm topicForm) throws MyException {
        Topic topic = new Topic();
        buildTopic(topic, topicForm);
        topicDAO.update(topic);
    }

    public void deleteUserAndPost(long idUser, long idTopic) {
        topicDAO.deleteUserAndPost(idUser, idTopic);
    }

    public Set<TopicForm> getAll() {
        Set<TopicForm> topicFormList = new HashSet<>();
        List<Topic> listTopic = topicDAO.getListTopic();
        listTopic.forEach(topic -> {
            TopicForm topicForm = new TopicForm(topic);
            topicFormList.add(topicForm);
        });
        return topicFormList;
    }

    public Set<TopicForm> getListTopic(Long idUser) {
        Set<TopicForm> topicForms = new HashSet<>();
        Set<Topic> listTopic = topicDAO.getListTopic(idUser);
        listTopic.forEach(topic -> {
            TopicForm topicForm = new TopicForm(topic);
            topicForms.add(topicForm);
        });
        return topicForms;
    }

    public Set<UserForm> getListUser(long idTopic) {
        Set<UserForm> userForms = new HashSet<>();
        Set<User> listUser = topicDAO.getListUser(idTopic);
        listUser.forEach(user -> {
            UserForm userForm = new UserForm(user);
            userForms.add(userForm);
        });
        return userForms;
    }

    private void buildTopic(Topic topic, TopicForm topicForm) {
        topic.setId(topicForm.getId());
        topic.setNameTopic(topicForm.getNameTopic());
        Set<User> users = new HashSet<>();
        topicForm.getUsers().forEach(userForm -> {
            User user = new User(userForm.getId(), userForm.getUserName(),
                    userForm.getPassword(), userForm.getEmail(), userForm.getRole(), userForm.getPhoto());
            users.add(user);
            topic.setUsers(users);
        });
    }
}
