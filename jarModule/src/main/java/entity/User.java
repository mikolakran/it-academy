package entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = "getByUserName", query = "select u from User u where u.userName = :name"),
        @NamedQuery(name = "getAllUser",query = "select u from User u")
})
/*@NamedQuery(name = "getByUserName", query = "select u from User u where u.userName = :name")*/
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
    private List<Topic> topic;

    public User() {
    }

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Topic> getTopic() {
        return topic;
    }

    public void addTopic(List<Topic> topic) {
        this.topic = topic;
    }
}
