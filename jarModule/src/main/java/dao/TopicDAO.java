package dao;

import entity.Topic;
import exception.CatchingCauseException;

import java.util.List;

public interface TopicDAO extends DAO<Topic>{

    List<Topic> getListTopic() throws CatchingCauseException;

}
