package com.pvt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;


@Data
@NoArgsConstructor
@Entity
@Table(name = "topic")
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
