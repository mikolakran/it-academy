package dao.impl;

import dao.TopicDAO;
import entity.Post;
import entity.Topic;
import entity.User;
import exception.LoginException;
import exception.MyException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import repository.TopicJpaRepository;

import java.util.List;
import java.util.Set;

@Repository
@Slf4j
public class TopicDAOImpl implements TopicDAO {

    @Autowired
    private TopicJpaRepository topicJpaRepository;

    @PersistenceContext
    @Getter
    protected EntityManager entityManager;

    @Override
    public Topic save(Topic topic) throws MyException {
        log.trace("TopicDAOImpl.save(Topic topic) " + topic);
        validationSQLSave(topic.getNameTopic());
        return topicJpaRepository.save(topic);
    }

    @Override
    public Topic get(Long aLong) {
        log.trace("TopicDAOImpl.get(Long aLong) along = " + aLong);
        return topicJpaRepository.findById(aLong).orElse(null);
    }

    @Override
    public void update(Topic topic) throws MyException {
        log.trace("TopicDAOImpl.update(Topic topic) topic = " + topic);
        validationSQLUpdate(topic.getNameTopic());
        topicJpaRepository.save(topic);
    }

    @Override
    public void delete(Long aLong) {
        log.trace("TopicDAOImpl.delete(Long aLong) aLong = " + aLong);
        topicJpaRepository.deleteById(aLong);
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
    public List<Topic> getListTopic() {
        log.trace("TopicDAOImpl.getListTopic()");
        return topicJpaRepository.findAll();
    }

    @Override
    public Set<Topic> getListTopic(long id) {
        log.trace("TopicDAOImpl.getListTopic(long idUser)");
        return topicJpaRepository.getListTopic(id).getTopic();
    }

    @Override
    public Set<User> getListUser(long id) {
        log.trace("TopicDAOImpl.getListTopic(long idTopic)");
        return topicJpaRepository.getListUser(id).getUsers();
    }

    @Override
    public Topic getByName(String name) {
        log.trace("TopicDAOImpl.getByName(String name) name = " + name);
        return topicJpaRepository.findByNameTopic(name);
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
