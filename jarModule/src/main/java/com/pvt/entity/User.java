package com.pvt.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //
    @Column(name = "name", unique = true, nullable = false, length = 55)
    private String userName;
    @Column(name = "password", nullable = false)
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
