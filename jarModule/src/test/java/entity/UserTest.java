package entity;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class UserTest {

    private User user;
    private Topic topic;
    private Post post;
    private Set<Topic> topics;
    private Set<Post> posts;

    @Before
    public void setUp() {
        user = new User(1, "radfar", "1234", "nikolai@mail.ru", "user");
        topic = new Topic(1, "topic1");
        Topic topic2 = new Topic(2, "topic2");
        post = new Post("post1", "abc");
        topics = new HashSet<>();
        topics.add(topic);
        topics.add(topic2);
        posts = new HashSet<>();
        posts.add(post);
        user.setTopic(topics);
        user.setPost(posts);
    }

    @Test
    public void getId() {
        assertEquals(1, user.getId());
    }

    @Test
    public void getUserName() {
        assertEquals("radfar", user.getUserName());
    }

    @Test
    public void getPassword() {
        assertEquals("1234", user.getPassword());
    }

    @Test
    public void getEmail() {
        assertEquals("nikolai@mail.ru", user.getEmail());
    }

    @Test
    public void getRole() {
        assertEquals("user", user.getRole());
    }

    @Test
    public void getTopic() {
        assertArrayEquals(new Topic[]{new Topic(1, "topic1"), new Topic(2, "topic1")}, user.getTopic().toArray());
    }

    @Test
    public void getPost() {
        assertArrayEquals(new Post[]{new Post("post1", "abc")}, user.getPost().toArray());
    }

    @Test
    public void setId() {
        user.setId(2);
        assertEquals(2, user.getId());
    }

    @Test
    public void setUserName() {
        user.setUserName("radfar2");
        assertEquals("radfar2", user.getUserName());
    }

    @Test
    public void setPassword() {
        user.setPassword("1234");
        assertEquals("1234", user.getPassword());
    }

    @Test
    public void setEmail() {
        user.setEmail("nik@mail.ru");
        assertEquals("nik@mail.ru", user.getEmail());
    }

    @Test
    public void setRole() {
        user.setRole("admin");
        assertEquals("admin", user.getRole());
    }

    @Test
    public void setTopic() {
        Set<Topic> topics1 = new HashSet<>();
        topics1.add(new Topic(3, "topic3"));
        user.setTopic(topics1);
        assertArrayEquals(new Topic[]{new Topic(3, "topic3")}, user.getTopic().toArray());
    }

    @Test
    public void setPost() {
        Set<Post> posts1 = new HashSet<>();
        posts1.add(new Post("post2", "abc"));
        user.setPost(posts1);
        assertArrayEquals(new Post[]{new Post("post2", "abc")}, user.getPost().toArray());
    }
}