
import dao.TopicDAO;
import dao.UserDAO;
import dao.impl.TopicDAOImpl;
import dao.impl.UserDAOImpl;
import entity.Topic;
import entity.User;
import exception.CatchingCauseException;
import exception.LoginException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Slf4j
public class TestHibernate {

    public static void main(String[] args) {
      User user = new User("radfast2","*s9C#nFSNx#A","mikolakran2@gmail.com","user");
      UserDAO userDAO = new UserDAOImpl();
      TopicDAO topicDAO = new TopicDAOImpl();
        // сохранение user and topic
        List<Topic> topics = new ArrayList<>();
        topics.add(new Topic("topic12"));
        topics.add(new Topic("topic13"));
        topics.add(new Topic("topic14"));
        topics.add(new Topic("topic15"));
        user.addTopic(topics);
        try {
            userDAO.save(user);
        } catch (LoginException | CatchingCauseException e) {
            System.out.println("sorry");
        }
        // добавляю topic to user
        Topic topic = new Topic("topic26");
        User user1 = new User();
        user1.setId(1);
        Set<User> users = new HashSet<>();
        users.add(user1);
        topic.setUsers(users);
        try {
            topicDAO.update(topic);
        } catch (LoginException | CatchingCauseException e) {
            System.out.println("sorry");
        }
        // delete topic
          try {
            topicDAO.delete(14);
        } catch (CatchingCauseException e) {
            System.out.println("sorry");
        }

        // добавляю topic to user

 /*       TopicDAO topicDAO = new TopicDAOImpl();
        Topic topic = new Topic("topic17");
        User user1 = new User();
        user1.setId(4);
        Set<User> users = new HashSet<>();
        users.add(user1);
        topic.setUsers(users);
        try {
            topicDAO.update(topic);
        } catch (LoginException | CatchingCauseException e) {
            System.out.println("sorry");
        }*/

    }
}
