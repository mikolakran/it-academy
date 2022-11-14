package dao;

import entity.Post;
import exception.CatchingCauseException;

import java.util.Set;

public interface PostDAO extends DAO<Post> {
    Set<Post> getListByIdUserPost(long idTopic, long idUser) throws CatchingCauseException;
}
