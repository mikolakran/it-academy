package dao.impl;

import connector.AbstractJPADAO;
import dao.TopicDAO;
import dao.UserDAO;
import entity.Topic;
import entity.User;
import exception.CatchingCauseException;
import exception.LoginException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import validation.CheckUser;
import validation.ValidationAuth;

import java.util.List;

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
        }finally {
            close();
        }
    }

    @Override
    public Topic get(long id) throws CatchingCauseException {
        return null;
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
                    log.error("UserDAOImplHibernate.update", ex);
                    throw new CatchingCauseException(e);
                }
            }
        }finally {
            close();
        }
    }

    @Override
    public void delete(long id) throws CatchingCauseException {
        try {
            init();
            entityManager.remove(entityManager.find(Topic.class,id));
        }catch (PersistenceException | IllegalArgumentException e) {
            entityManager.getTransaction().rollback();
            log.error("TopicDAOImpl.delete", e);
            throw new CatchingCauseException(e);
        }finally {
            close();
        }
    }

    @Override
    public List<Topic> getListTopic() throws CatchingCauseException {
        List<Topic> topics;
        try {
            init();
            TypedQuery<Topic> typedQuery = entityManager.createNamedQuery("getAllTopic", Topic.class);
            topics = typedQuery.getResultList();
        } catch (PersistenceException | IllegalStateException e) {
            entityManager.getTransaction().rollback();
            log.error("UserDAOImpl.getListUsers", e);
            throw new CatchingCauseException(e);
        }finally {
            close();
        }
        return topics;
    }
}
