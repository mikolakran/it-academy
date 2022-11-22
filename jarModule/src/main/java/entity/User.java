package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = "getByUserName", query = "select u from User u where u.userName = :name"),
        @NamedQuery(name = "getAllUser", query = "select u from User u"),
        @NamedQuery(name = "getAllTopic", query = "SELECT u FROM User u LEFT JOIN FETCH u.topic  where u.id = : id"),
        @NamedQuery(name = "getAdmin", query = "SELECT u FROM User u where u.role = : role")
})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //
    @Column(name = "name", unique = true, nullable = false, length = 55)
    private String userName;
    @Column(name = "password", nullable = false, length = 55)
    private String password;
    @Column(name = "email", unique = true, nullable = false, length = 55)
    private String email;
    @Column(name = "role", nullable = false, length = 55)
    private String role;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_topic",
            joinColumns = {@JoinColumn(name = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "topicId")}
    )
    private Set<Topic> topic;

    @OneToMany(mappedBy = "user")
    private Set<Post> post;

    public User(long id, String userName, String password, String email, String role) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User(String userName, String password, String email, String role) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

}
