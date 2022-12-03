package entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PostTest {

    private Post post;

    @Before
    public void setUp() {
        post = new Post("post1","abc");
        User user = new User(1, "radfar", "1234", "nikolai@mail.ru", "user");
        Topic topic =  new Topic(1,"topic1");
        post.setId(1);
        post.setUser(user);
        post.setTopic(topic);
    }

    @Test
   public void getId() {
        assertEquals(1,post.getId());
    }

    @Test
    public void getName() {
        assertEquals("post1",post.getName());
    }

    @Test
    public void getText() {
        assertEquals("abc",post.getText());
    }

    @Test
    public void getUser() {
        assertEquals(new User(1, "radfar", "1234", "nikolai@mail.ru", "user"),
                post.getUser());
    }

    @Test
    public void getTopic() {
        assertEquals(new Topic(1,"topic1"),post.getTopic());
    }

    @Test
    public void setId() {
        post.setId(2);
        assertEquals(2,post.getId());
    }

    @Test
    public void setName() {
        post.setName("post2");
        assertEquals("post2",post.getName());
    }

    @Test
    public void setText() {
        post.setText("abc2");
        assertEquals("abc2",post.getText());
    }

    @Test
    public void setUser() {
        post.setUser(new User(2, "radfar2", "12342", "nikolai2@mail.ru", "admin"));
        assertEquals(new User(2, "radfar2", "12342", "nikolai2@mail.ru", "admin"),
                post.getUser());
    }

    @Test
    public void setTopic() {
        post.setTopic(new Topic(2,"topic2"));
        assertEquals(new Topic(2,"topic2"),post.getTopic());
    }
}