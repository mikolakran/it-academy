package dao.impl;

import configurations.AppConfig;
import dao.PostDAO;
import dao.TopicDAO;
import dao.UserDAO;
import entity.Post;
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

import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class PostDAOImplTest {



    @Autowired
    private PostDAO postDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private TopicDAO topicDAO;

    private Post post;
    private User user;
    private Topic topic;

    private final String NAME = "Post1";
    private final String TEXT = "hibernate";


    @Before
    public void setUp()  {
        post = new Post(NAME,TEXT);
        post.setId(46);


    }

    @Test
    @Ignore
    public void save() throws MyException {
        user = userDAO.getByName("Nikolai3");
        topic = topicDAO.getByName("topic3");
        Set<User> users = userDAO.getListUsersWhereIdTopic(topic.getId());
        users.add(user);
        topic.setUsers(users);
        topicDAO.update(topic);

        post.setUser(user);
        post.setTopic(topic);
            postDAO.save(post);
        assertEquals(post.toString(),postDAO.get(post.getId()).toString());
    }

    @Test
    @Ignore
    public void get() {
        Post result = postDAO.get(post.getId());
        assertEquals(post.getName(),result.getName());
    }

    @Test
    @Ignore
    public void update() throws MyException {
        Post result = postDAO.get(post.getId());
        result.setName("post");
        result.setText("springHibernate");
        postDAO.update(result);
        assertEquals("post",postDAO.get(post.getId()).getName());
        assertEquals("springHibernate",postDAO.get(post.getId()).getText());
    }

    @Test
    @Ignore
    public void delete() {
        Post post1 = postDAO.get(post.getId());
        postDAO.delete(post1.getId());
        assertNull(postDAO.get(post1.getId()));
    }

    @Test
    @Ignore
    public void getListByIdUserPost() {
        User user1 = userDAO.getByName("Nikolai3");
        Topic topic1 = topicDAO.getByName("topic3");
        Set<Post> posts = postDAO.getListByIdUserPost(topic1.getId(), user1.getId());
        assertFalse(posts.isEmpty());
    }
}