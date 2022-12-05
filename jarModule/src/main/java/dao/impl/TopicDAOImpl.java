package dao.impl;

import dao.BaseDAO;
import dao.TopicDAO;
import entity.Post;
import entity.Topic;
import entity.User;
import exception.CatchingCauseException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
@Slf4j
public class TopicDAOImpl extends BaseDAO<Topic,Long> implements TopicDAO {

    @Transactional(rollbackOn = {CatchingCauseException.class})
    public void deleteTopic(long idUser, long idTopic) throws CatchingCauseException {
        try {
            PostDAOImpl postDAO = new PostDAOImpl();
            Set<Post> listByIdUserPost = postDAO.getListByIdUserPost(idTopic, idUser);
            if (listByIdUserPost.size()!=0){
                for (Post post:listByIdUserPost) {
                    postDAO.delete(post.getId());
                }
            }
            User user = entityManager.find(User.class, idUser);
            user.getTopic().removeIf(topic -> topic.getId() == idTopic);
            entityManager.merge(user);
        } catch (PersistenceException | IllegalArgumentException e) {
            log.error("TopicDAOImpl.deleteTopic", e);
            throw new CatchingCauseException(e);
        }
    }

    @Override
    public Set<Topic> getListTopic(long id) throws CatchingCauseException {
        Set<Topic> topics = new HashSet<>();
        try {
            TypedQuery<User> typedQuery = entityManager.createNamedQuery("getAllTopic", User.class).
                    setParameter("id", id);
            User user = typedQuery.getSingleResult();
            topics.addAll(user.getTopic());
        } catch (PersistenceException | IllegalStateException e) {
            log.error("TopicDAOImpl.getListTopic", e);
            throw new CatchingCauseException(e);
        }
        return topics;
    }
}
