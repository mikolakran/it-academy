package org.web.forms;

import entity.Topic;
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
    private Long idAddTopic;

    public TopicForm(Topic topic) {
        this.id = topic.getId();
        this.nameTopic = topic.getNameTopic();
    }

    public TopicForm(long id, String nameTopic) {
        this.id = id;
        this.nameTopic = nameTopic;
    }

    @Override
    public String toString() {
        return "TopicForm{" +
                "id=" + id +
                ", nameTopic='" + nameTopic + '\'' +
                '}';
    }
}
