package org.web.facades;

import dao.TopicDAO;
import entity.Topic;
import entity.User;
import exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web.forms.TopicForm;
import org.web.forms.UserForm;

import java.util.HashSet;
import java.util.Set;

@Component
public class TopicFacade {
    @Autowired
    private TopicDAO topicDAO;

    public TopicForm save(TopicForm topicForm) throws MyException {
        Topic topic = new Topic();
        buildTopic(topic,topicForm);
        Topic resultSave = topicDAO.save(topic);
        return new TopicForm(resultSave);
    }

    public Set<TopicForm> getListTopic(Long idUser){
        Set<TopicForm> topicForms = new HashSet<>();
        Set<Topic> listTopic = topicDAO.getListTopic(idUser);
        listTopic.forEach(topic -> {
            TopicForm topicForm = new TopicForm(topic);
            topicForms.add(topicForm);
        });
        return topicForms;
    }



    private void buildTopic(Topic topic, TopicForm topicForm){
        topic.setId(topicForm.getId());
        topic.setNameTopic(topicForm.getNameTopic());
        topicForm.getUsers().forEach(userForm -> {
            Set<User> users = new HashSet<>();
            User user = new User(userForm.getId(), userForm.getUserName(),
                    userForm.getPassword(), userForm.getEmail(), userForm.getRole());
            users.add(user);
            topic.setUsers(users);
        });
    }
}
