package dao;

import entity.Topic;
import exception.CatchingCauseException;

import java.util.Set;

public interface TopicDAO extends DAO<Topic> {

    Set<Topic> getListTopic(long id) throws CatchingCauseException;

    void deleteTopic(long idUser, long idTopic) throws CatchingCauseException;

}
