package dao;

import entity.Post;
import entity.Topic;
import entity.User;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class TopicDAOTest {
    private Topic topic;

    @Before
    public void setUp() {
        topic = new Topic(1,"topic1");
        Set<User> users = new HashSet<>();
        Set<Post> posts = new HashSet<>();
        Post post = new Post("post1","abc");
        posts.add(post);
        User user = new User(1, "radfar", "1234", "nikolai@mail.ru", "user");
        users.add(user);
        topic.setPost(posts);
        topic.setUsers(users);
    }

    @Test
    public void getId() {
        assertEquals(1,topic.getId());
    }

    @Test
    public void getNameTopic() {
        assertEquals("topic1",topic.getNameTopic());
    }

    @Test
    public  void getUsers() {
        assertArrayEquals(new User[]
                        {new User(1, "radfar", "1234", "nikolai@mail.ru", "user")},
                topic.getUsers().toArray());
    }

    @Test
    public  void getPost() {
        assertArrayEquals(new Post[]{new Post("post1","abc")},
                topic.getPost().toArray());
    }

    @Test
    public void setId() {
        topic.setId(2);
        assertEquals(2,topic.getId());
    }

    @Test
    public void setNameTopic() {
        topic.setNameTopic("topic2");
        assertEquals("topic2",topic.getNameTopic());
    }

    @Test
    public  void setUsers() {
        Set<User> users1 = new HashSet<>();
        users1.add(new User(2, "radfar2", "12342", "nikolai2@mail.ru", "admin"));
        topic.setUsers(users1);
        assertArrayEquals(new User[]
                        {new User(2, "radfar2", "12342", "nikolai2@mail.ru", "admin")},
                topic.getUsers().toArray());
    }

    @Test
    public  void setPost() {
        Set<Post> posts1 = new HashSet<>();
        posts1.add(new Post("post2","abc2"));
        topic.setPost(posts1);
        assertArrayEquals(new Post[]{new Post("post2","abc2")},
                topic.getPost().toArray());
    }
}