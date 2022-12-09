package dao;

import entity.Post;

import java.util.Set;

public interface PostDAO extends DAO<Post, Long> {
    Set<Post> getListByIdUserPost(long idTopic, long idUser);
}
