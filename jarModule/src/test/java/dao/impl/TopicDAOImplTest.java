package dao.impl;

import configurations.AppConfig;
import dao.TopicDAO;
import dao.UserDAO;
import entity.Topic;
import entity.User;
import exception.CatchingCauseException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class TopicDAOImplTest {

    @Autowired
    private TopicDAO topicDAO;

    @Autowired
    private UserDAO userDAO;

    private User user;

    @Before
    public void setUp() {
        user = new User("Nikolai10", "1234", "mikola10@mail.ru", "user");
    }

    @Test
    @Ignore
    public void deleteTopic() {
        try {
            User resultUser = userDAO.get(3L);
//            topicDAO.deleteTopic(3L,2);
            Set<Topic> topic = resultUser.getTopic();
            for (Topic topic1 : topic) {
                System.out.println("topic1 = " + topic1);
            }
        } catch (CatchingCauseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getListTopic() {
    }
}