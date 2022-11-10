package entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name = "topic")
@NamedQueries({
        @NamedQuery(name = "getAllTopic", query = "SELECT t from Topic t")
})
public class Topic implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTopic")
    private long id;
    @Column(name = "nameTopic", unique = true, nullable = false, length = 55)
    private String nameTopic;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_topic",
            joinColumns = {@JoinColumn(name = "topicId")},
            inverseJoinColumns = {@JoinColumn(name = "userId")}
    )
    private Set<User> users;

    public Topic() {
    }

    public Topic(String nameTopic) {
        this.nameTopic = nameTopic;
    }

    public Topic(long id, String nameTopic) {
        this.id = id;
        this.nameTopic = nameTopic;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameTopic() {
        return nameTopic;
    }

    public void setNameTopic(String nameTopic) {
        this.nameTopic = nameTopic;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", nameTopic='" + nameTopic + '\'' +
                '}';
    }
}
