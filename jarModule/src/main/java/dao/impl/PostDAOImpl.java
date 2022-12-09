package dao.impl;

import dao.BaseDAO;
import dao.PostDAO;
import entity.Post;
import exception.MyException;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
@Slf4j
public class PostDAOImpl extends BaseDAO<Post, Long> implements PostDAO {

    public PostDAOImpl() {
        super();
        aClass = Post.class;
    }

    @Override
    public void save(Post post) throws MyException {
        log.trace("PostDAOImpl.save(Post post) post = " + post);
        super.save(post);
    }

    @Override
    public Post get(Long id) {
        log.trace("PostDAOImpl.get(Long id) id = " + id);
        return entityManager.find(Post.class, id);
    }

    @Override
    public void update(Post post) {
        log.trace("PostDAOImpl.update(Post post) post = " + post);
        entityManager.merge(post);
    }

    @Override
    public void delete(Long id) {
        log.trace("PostDAOImpl.delete(Long id) id = " + id);
        entityManager.remove(entityManager.find(Post.class, id));
    }

    @Override
    public Set<Post> getListByIdUserPost(long idTopic, long idUser) {
        log.trace("PostDAOImpl.getListByIdUserPost(long idTopic, long idUser) idTopic = " + idTopic + " idUser = " + idUser);
        TypedQuery<Post> typedQuery = entityManager.createNamedQuery("getAllPost", Post.class).
                setParameter("idTopic", idTopic).setParameter("idUser", idUser);
        return new HashSet<>(typedQuery.getResultList());
    }

}
