package org.example.registration.user;

import java.util.Objects;

public class User {
    private long id;
    private String userName;
    private String password;
    private String email;
    private String role;

    public User() {
    }

    public User(long id, String username, String password, String email, String role) {
        this.id = id;
        this.userName = username;
        this.password = password;
        this.email = email;
        this.role = role;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

       @Override
        public String toString() {
            return "    \""+id+"\" : {\n" +
                    "      \"id\" : \""+id+"\"\n" +
                    "      \"userName\" : \""+ userName +"\"\n" +
                    "      \"password\" : \""+password+"\"\n" +
                    "      \"email\" : \""+email+"\"\n" +
                    "      \"role\" : \""+role+"\"\n" +
                    "    },";
        }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!Objects.equals(userName, user.userName)) return false;
        if (!Objects.equals(password, user.password)) return false;
        if (!Objects.equals(email, user.email)) return false;
        return Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
