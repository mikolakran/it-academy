package com.web.facades;

import com.pvt.dao.PostDAO;
import com.pvt.entity.Post;
import com.pvt.entity.Topic;
import com.pvt.entity.User;
import com.pvt.exception.MyException;
import com.web.forms.PostForm;
import com.web.forms.TopicForm;
import com.web.forms.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PostFacade {

    @Autowired
    private PostDAO postDAO;

    @Autowired
    private EmailService emailService;



    public void save(PostForm postForm) throws MyException {
        Post post = new Post();
        buildPost(post, postForm);
        postDAO.save(post);
        String body = "Post " + postForm.getName() + " has create for topic " + post.getTopic().getNameTopic();
        emailService.sendEmail("mao85@tut.by",
                post.getUser().getEmail(),
                "Test",
                body);
    }

    public PostForm get(Long id) {
        Post post = postDAO.get(id);
        return new PostForm(post);
    }

    public void update(PostForm postForm) throws MyException {
        Post post = postDAO.get(postForm.getId());
        post.setName(postForm.getName());
        post.setText(postForm.getText());
        postDAO.update(post);
    }

    public void delete(Long id) {
        postDAO.delete(id);
    }

    public Set<PostForm> getListByIdUserPost(long idTopic, long idUser) {
        Set<Post> listByIdUserPost = postDAO.getListByIdUserPost(idTopic, idUser);
        Set<PostForm> postForms = new HashSet<>();
        listByIdUserPost.forEach(post -> {
            PostForm postForm = new PostForm(post);
            postForms.add(postForm);
        });
        return postForms;
    }

    private void buildPost(Post post, PostForm postForm) {
        post.setId(postForm.getId());
        post.setName(postForm.getName());
        post.setText(postForm.getText());
        UserForm userForm = postForm.getUser();
        if (userForm != null) {
            User user = new User(userForm.getId(), userForm.getUserName(), userForm.getPassword(),
                    userForm.getEmail(), userForm.getRole());
            post.setUser(user);
        }
        TopicForm topicForm = postForm.getTopic();
        if (topicForm != null) {
            Topic topic = new Topic(topicForm.getId(), topicForm.getNameTopic());
            post.setTopic(topic);
        }
    }
}
