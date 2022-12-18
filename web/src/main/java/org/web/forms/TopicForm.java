package org.web.forms;

import entity.Post;
import entity.Topic;
import entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicForm implements Serializable {
    private long id;
    private String nameTopic;
    private Set<UserForm> users;
    private Set<Post> post;
    private Set<TopicForm> topics;

    public TopicForm(Topic topic){
        this.id = topic.getId();
        this.nameTopic = topic.getNameTopic();
    }

}
