package com.web.forms;


import com.pvt.entity.Post;
import com.pvt.entity.Topic;
import com.pvt.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostForm implements Serializable {
    private long id;
    private String name;
    private String text;
    private UserForm user;
    private TopicForm topic;
    private Long idTopic;

    public PostForm(Post post) {
        this.id = post.getId();
        this.name = post.getName();
        this.text = post.getText();
        User resultUser = post.getUser();
        Topic resultTopic = post.getTopic();
        this.user = new UserForm(resultUser.getId(), resultUser.getUserName(),
                resultUser.getPassword(), resultUser.getEmail(), resultUser.getRole());
        this.topic = new TopicForm(resultTopic.getId(), resultTopic.getNameTopic());
    }

    @Override
    public String toString() {
        return "PostForm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
