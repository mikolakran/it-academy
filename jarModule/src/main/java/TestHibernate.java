
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
      // saveUserWithTopics();
       addTopicToUser2();
       // deleteTopic(10);
    }

    private static void addTopicToUser2() {
        TopicDAO topicDAO = new TopicDAOImpl();
        Topic topic = new Topic("topic56");
        User user1 = new User();
        user1.setId(4);
        Set<User> users = new HashSet<>();
        users.add(user1);
        topic.setUsers(users);
        try {
            topicDAO.update(topic);
        } catch (Exception e) {
            System.out.println("sorry");
        }
    }

    private static String saveUserWithTopics() {
        User user = new User("radfast2","*s9C#nFSNx#A","mikolakran2@gmail.com","user");
        UserDAO userDAO = new UserDAOImpl();
        List<Topic> topics = new ArrayList<>();
        topics.add(new Topic("topic12"));
        topics.add(new Topic("topic13"));
        topics.add(new Topic("topic14"));
        topics.add(new Topic("topic15"));
        user.addTopic(topics);
        try {
            userDAO.save(user);
        } catch (Exception e) {
            System.out.println("sorry");
        }
        return user.getUserName();
    }

    private static void addTopicToUser(String userName, String topicName){
        UserDAO userDAO = new UserDAOImpl();
        User user = null;
        try {
            user = userDAO.getByName(userName);
        } catch (Exception e) {
            System.out.println("sorry");
        }
        Topic topic = new Topic(topicName);
        if (user != null) {
            user.getTopic().add(topic);
            try {
                userDAO.update(user);
            } catch (Exception e) {
                System.out.println("sorry");
            }
        }
    }

    private static void deleteTopic(long id){
        TopicDAO topicDAO = new TopicDAOImpl();
        try {
            topicDAO.delete(id);
        } catch (Exception e) {
            System.out.println("sorry");
        }
    }
}
