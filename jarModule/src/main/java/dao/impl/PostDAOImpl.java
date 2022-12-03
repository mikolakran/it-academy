package dao.impl;

import connector.AbstractJPADAO;
import dao.PostDAO;
import entity.Post;
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
public class PostDAOImpl extends AbstractJPADAO implements PostDAO {

    private final ValidationAuth checkUser = new CheckUser();

    @Override
    public void save(Post post) throws LoginException, CatchingCauseException {
        try {
            init();
            entityManager.persist(post);
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            try {
                CatchingCauseException.findPathToCause(e);
            } catch (CatchingCauseException ex) {
                if (checkUser.validationSQL(ex)) {
                    log.error("PostDAOImpl.save", ex);
                    throw new CatchingCauseException(e);
                }
            }
        } finally {
            close();
        }
    }

    @Override
    public Post get(Long id) throws CatchingCauseException {
        Post post;
        try {
            init();
            post = entityManager.find(Post.class, id);
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            log.error("PostDAOImpl.get", e);
            throw new CatchingCauseException(e);
        } finally {
            close();
        }
        return post;
    }

    @Override
    public void update(Post post) throws LoginException, CatchingCauseException {
        try {
            init();
            entityManager.merge(post);
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            try {
                CatchingCauseException.findPathToCause(e);
            } catch (CatchingCauseException ex) {
                if (checkUser.validationSQL(ex)) {
                    log.error("PostDAOImpl.update", ex);
                    throw new CatchingCauseException(e);
                }
            }
        } finally {
            close();
        }
    }

    @Override
    public void delete(Long id) throws CatchingCauseException {
        try {
            init();
            entityManager.remove(entityManager.find(Post.class, id));
        } catch (PersistenceException | IllegalArgumentException e) {
            entityManager.getTransaction().rollback();
            log.error("PostDAOImpl.delete", e);
            throw new CatchingCauseException(e);
        } finally {
            close();
        }
    }

    @Override
    public Set<Post> getListByIdUserPost(long idTopic, long idUser) throws CatchingCauseException {
        Set<Post> posts = new HashSet<>();
        try {
            init();
            TypedQuery<Post> typedQuery = entityManager.createNamedQuery("getAllPost", Post.class).
                    setParameter("idTopic", idTopic).setParameter("idUser", idUser);
            posts.addAll(typedQuery.getResultList());
        } catch (PersistenceException | IllegalStateException e) {
            entityManager.getTransaction().rollback();
            log.error("PostDAOImpl.getListPost", e);
            throw new CatchingCauseException(e);
        } finally {
            close();
        }
        return posts;
    }

}
