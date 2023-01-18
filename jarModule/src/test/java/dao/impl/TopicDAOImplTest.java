package dao.impl;

import configurations.AppConfig;
import dao.PostDAO;
import dao.TopicDAO;
import dao.UserDAO;
import entity.Topic;
import entity.User;
import exception.MyException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class TopicDAOImplTest {

    @Autowired
    private TopicDAO topicDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PostDAO postDAO;

    private Topic topic;

    private final String NAME = "topic20";
    private final String NAME2 = "topic21";
    private final String NAME3 = "topic22";

    @Before
    public void setUp() {
        topic = new Topic(NAME);
    }

    @Test
    @Ignore
    public void save() throws MyException {
        User result = userDAO.getByName("mikolai");
        Topic topic1 = new Topic(NAME3);
        Set<User> users = new HashSet<>();
        users.add(result);
        topic1.setUsers(users);
        topicDAO.save(topic1);
        assertEquals(topic1.getNameTopic(),topicDAO.getByName(NAME3).getNameTopic());
    }

    @Test
    @Ignore
    public void throwsExceptionForSave(){
        assertThrows(MyException.class,()->topicDAO.save(topic));
    }

    @Test
    @Ignore
    public void get(){
        Topic result = topicDAO.getByName(NAME);
        assertEquals(result.toString(),topicDAO.get(result.getId()).toString());
        assertNull(topicDAO.get(234L));
    }

    @Test
    @Ignore
    public void update() throws MyException {
        User result = userDAO.getByName("Nikolai4");
        Topic topic1 = topicDAO.getByName(NAME);
        Set<User> listUsers = userDAO.getListUsersWhereIdTopic(topic1.getId());
        listUsers.add(result);
        topic1.setUsers(listUsers);
        topicDAO.update(topic1);
        assertArrayEquals(topic1.getUsers().toArray(),topicDAO.getListUser(topic1.getId()).toArray());
    }

    @Test
    @Ignore
    public void delete(){
        //delete topic and user_topic
        Topic topic1 = topicDAO.getByName("topic7");
        topicDAO.delete(topic1.getId());
        assertNull(topicDAO.get(topic1.getId()));
    }

    @Test
    @Ignore
    public void deleteUser_TopicAndPost() {
        // delete user_topic and posts or post
        User user = userDAO.get(5L);
        Topic topic1 = topicDAO.get(3L);
        topicDAO.deleteUserAndPost(user.getId(), topic1.getId());

        Set<User> users = topicDAO.getListUser(topic1.getId());
        User user2 = new User();
        for (User user1 : users) {
            if (user1.getUserName().equals(user.getUserName())){
                user2 = user1;
            }
        }

        assertNull(user2.getUserName());
        assertTrue(postDAO.getListByIdUserPost(topic1.getId(), user.getId()).isEmpty());
    }

    @Test
    @Ignore
    public void getAll(){
        List<Topic> listTopic = topicDAO.getListTopic();
        System.out.println("listTopic.size() = " + listTopic.size());
        assertNotNull(listTopic);
    }

    @Test
    @Ignore
    public void getListUserInTopic() {
        Topic topic2 = topicDAO.getByName(NAME);
        assertArrayEquals(topicDAO.getListUser(topic2.getId()).toArray(),
                userDAO.getListUsersWhereIdTopic(topic2.getId()).toArray());
    }

    @Test
    @Ignore
    public void getByName(){
        Topic topic1 = topicDAO.getByName(NAME);
        assertEquals(topic1.getNameTopic(),topicDAO.getByName(topic1.getNameTopic()).getNameTopic());
    }

}