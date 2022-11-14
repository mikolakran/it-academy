package dao.impl;

import connector.AbstractJPADAO;
import dao.TopicDAO;
import entity.Topic;
import entity.User;
import exception.CatchingCauseException;
import exception.LoginException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import validation.CheckUser;
import validation.ValidationAuth;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class TopicDAOImpl extends AbstractJPADAO implements TopicDAO {
    private final ValidationAuth checkUser = new CheckUser();

    @Override
    public void save(Topic topic) throws LoginException, CatchingCauseException {
        try {
            init();
            entityManager.persist(topic);
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            try {
                CatchingCauseException.findPathToCause(e);
            } catch (CatchingCauseException ex) {
                if (checkUser.validationSQL(ex)) {
                    log.error("TopicDAOImpl.save", ex);
                    throw new CatchingCauseException(e);
                }
            }
        } finally {
            close();
        }
    }

    @Override
    public Topic get(long id) throws CatchingCauseException {
        Topic topic;
        try {
            init();
            topic = entityManager.find(Topic.class, id);
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            log.error("TopicDAOImpl.get", e);
            throw new CatchingCauseException(e);
        } finally {
            close();
        }
        return topic;
    }

    @Override
    public void update(Topic topic) throws LoginException, CatchingCauseException {
        try {
            init();
            entityManager.merge(topic);
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            try {
                CatchingCauseException.findPathToCause(e);
            } catch (CatchingCauseException ex) {
                if (checkUser.validationSQL(ex)) {
                    log.error("TopicDAOImpl.update", ex);
                    throw new CatchingCauseException(e);
                }
            }
        } finally {
            close();
        }
    }

    @Override
    public void delete(long id) throws CatchingCauseException {
        try {
            init();
            entityManager.remove(entityManager.find(Topic.class, id));
        } catch (PersistenceException | IllegalArgumentException e) {
            entityManager.getTransaction().rollback();
            log.error("TopicDAOImpl.delete", e);
            throw new CatchingCauseException(e);
        } finally {
            close();
        }
    }

    public void deleteTopic(long idUser, long idTopic) throws CatchingCauseException {
        try {
            init();
            User user = entityManager.find(User.class, idUser);
            user.getTopic().removeIf(topic -> topic.getId() == idTopic);
            entityManager.merge(user);
        } catch (PersistenceException | IllegalArgumentException e) {
            entityManager.getTransaction().rollback();
            log.error("TopicDAOImpl.deleteTopic", e);
            throw new CatchingCauseException(e);
        } finally {
            close();
        }
    }

    @Override
    public Set<Topic> getListTopic(long id) throws CatchingCauseException {
        Set<Topic> topics = new HashSet<>();
        try {
            init();
            TypedQuery<User> typedQuery = entityManager.createNamedQuery("getAllTopic", User.class).
                    setParameter("id", id);
            User user = typedQuery.getSingleResult();
            topics.addAll(user.getTopic());
        } catch (PersistenceException | IllegalStateException e) {
            entityManager.getTransaction().rollback();
            log.error("TopicDAOImpl.getListTopic", e);
            throw new CatchingCauseException(e);
        } finally {
            close();
        }
        return topics;
    }
}
