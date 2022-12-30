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
        @NamedQuery(name = "getByUserEmail", query = "select u from User u where u.email = :email"),
        @NamedQuery(name = "getAllUser", query = "select u from User u"),
        @NamedQuery(name = "getAllUserWhereIdTopic", query = "SELECT t FROM Topic t LEFT JOIN FETCH t.users  where t.id = : id"),
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
    @Lob
    @Column(name = "image",length = Integer.MAX_VALUE)
    private byte[] image;

    @ManyToMany(mappedBy = "users")
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

    public User(long id, String userName, String password, String email, String role, byte[] image) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
        this.image = image;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
