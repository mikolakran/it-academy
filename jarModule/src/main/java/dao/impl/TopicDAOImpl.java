package dao.impl;

import dao.BaseDAO;
import dao.TopicDAO;
import entity.Post;
import entity.Topic;
import entity.User;
import exception.LoginException;
import exception.MyException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@Slf4j
public class TopicDAOImpl extends BaseDAO<Topic, Long> implements TopicDAO {

    public TopicDAOImpl() {
        super();
        aClass = Topic.class;
    }

    @Override
    public Topic save(Topic topic) throws MyException {
        log.trace("TopicDAOImpl.save(Topic topic) " + topic);
        validationSQLSave(topic.getNameTopic());
        return super.save(topic);
    }

    @Override
    public Topic get(Long aLong) {
        log.trace("TopicDAOImpl.get(Long aLong) along = " + aLong);
        return super.get(aLong);
    }

    @Override
    public void update(Topic topic) throws MyException {
        log.trace("TopicDAOImpl.update(Topic topic) topic = " + topic);
        validationSQLUpdate(topic.getNameTopic());
        super.update(topic);
    }

    @Override
    public void delete(Long aLong) {
        log.trace("TopicDAOImpl.delete(Long aLong) aLong = " + aLong);
        super.delete(aLong);
    }

    @Transactional
    @Override
    public void deleteUserAndPost(long idUser, long idTopic) {
        log.trace("TopicDAOImpl.delete(long idUser, long idTopic) idUser = " + idUser + " idTopic = " + idTopic);
        Topic topic = get(idTopic);
        getListUser(idTopic).removeIf(user -> user.getId() == idUser);
        TypedQuery<Post> typedQuery = entityManager.createNamedQuery("getAllPost", Post.class).
                setParameter("idTopic", idTopic).setParameter("idUser", idUser);
        typedQuery.getResultList().forEach(post1 -> entityManager.remove(entityManager.find(Post.class, post1.getId())));
        entityManager.merge(topic);
    }

    @Override
    public Set<Topic> getListTopic(long id) {
        log.trace("TopicDAOImpl.getListTopic(long idUser)");
        TypedQuery<User> typedQuery = entityManager.createNamedQuery("getAllTopic", User.class).
                setParameter("id", id);
        User user = typedQuery.getSingleResult();
        return user.getTopic();
    }

    @Override
    public Set<User> getListUser(long id) {
        log.trace("TopicDAOImpl.getListTopic(long idUser)");
        TypedQuery<Topic> typedQuery = entityManager.createNamedQuery("getAllTopicUsers", Topic.class).
                setParameter("id", id);
        Topic topic = typedQuery.getSingleResult();
        return topic.getUsers();
    }

    @Override
    public Topic getByName(String name) {
        Topic topic = null;
        TypedQuery<Topic> typedQuery = entityManager.createNamedQuery("getByTopicName", Topic.class).
                setParameter("name", name);
        List<Topic> resultList = typedQuery.getResultList();
        if (resultList.size() != 0) {
            for (Topic topic1 : resultList) {
                topic = topic1;
            }
        }
        log.trace("TopicDAOImpl.getByName(String name) name = " + name + " topic = " + topic);
        return topic;
    }

    private void validationSQLSave(String topicName) throws LoginException {
        if (getByName(topicName) != null) {
            log.error("TopicDAOImpl.validationSQL(String topicName) ",
                    new Throwable("topicName.nameTopic != null = " + topicName));
            throw new LoginException("name exist");
        } else {
            log.trace("TopicDAOImpl.save(Topic topicName) = true");
        }
    }

    private void validationSQLUpdate(String topicName) throws LoginException {
        if (getByName(topicName) == null) {
            log.error("TopicDAOImpl.validationSQLUpdate(String topicName) ",
                    new Throwable("topicName.nameTopic == null "));
            throw new LoginException("Topic null");
        } else {
            log.trace("TopicDAOImpl.update(Topic topicName) = true");
        }
    }

}
