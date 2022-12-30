package dao;

import entity.Topic;
import entity.User;

import java.util.List;
import java.util.Set;

public interface TopicDAO extends DAO<Topic, Long> {

    @Deprecated
    @Override
    void delete(Long aLong);//delete topic and user_topic

    @Deprecated
    Topic getByName(String name);// using for TopicDAOImpl.class and test
    List<Topic> getListTopic();

    Set<Topic> getListTopic(long id);

    Set<User> getListUser(long id);

    void deleteUserAndPost(long idUser, long idTopic);

}
