package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;


@Data
@NoArgsConstructor
@Entity
@Table(name = "topic")
@NamedQueries({
        @NamedQuery(name = "getByTopicName", query = "select t from Topic t where t.nameTopic = :name"),
        @NamedQuery(name = "getAllTopic", query = "SELECT u FROM User u LEFT JOIN FETCH u.topic  where u.id = : id"),
        @NamedQuery(name = "getAllTopicUsers", query = "SELECT t FROM Topic t LEFT JOIN FETCH t.users  where t.id = : id")
})
public class Topic implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTopic")
    private long id;
    @Column(name = "nameTopic", unique = true, nullable = false, length = 55)
    private String nameTopic;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "user_topic",
            joinColumns = {@JoinColumn(name = "topicId")},
            inverseJoinColumns = {@JoinColumn(name = "userId")}
    )
    private Set<User> users;

    @OneToMany(mappedBy = "topic")
    private Set<Post> post;

    public Topic(String nameTopic) {
        this.nameTopic = nameTopic;
    }

    public Topic(long id, String nameTopic) {
        this.id = id;
        this.nameTopic = nameTopic;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", nameTopic='" + nameTopic + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Topic topic = (Topic) o;

        return id == topic.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
