package com.pvt.dao.impl;


import com.pvt.dao.PostDAO;
import com.pvt.entity.Post;
import com.pvt.exception.MyException;
import com.pvt.repository.PostJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@Slf4j
public class PostDAOImpl implements PostDAO {

    @Autowired
    private PostJpaRepository postJpaRepository;

    @Override
    public Post save(Post post) throws MyException {
        log.trace("PostDAOImpl.save(Post post) post = " + post);
        return postJpaRepository.save(post);
    }

    @Override
    public Post get(Long id) {
        log.trace("PostDAOImpl.get(Long id) id = " + id);
        return postJpaRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Post post) {
        log.trace("PostDAOImpl.update(Post post) post = " + post);
        postJpaRepository.save(post);
    }

    @Override
    public void delete(Long id) {
        log.trace("PostDAOImpl.delete(Long id) id = " + id);
        postJpaRepository.delete(get(id));
    }

    @Override
    public Set<Post> getListByIdUserPost(long idTopic, long idUser) {
        log.trace("PostDAOImpl.getListByIdUserPost(long idTopic, long idUser) idTopic = " + idTopic + " idUser = " + idUser);
        return postJpaRepository.getListByIdUserPost(idTopic,idUser);
    }

}
