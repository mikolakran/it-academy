package org.web.forms;

import entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForm implements Serializable {
    private long id;
    private String userName;
    private String password;
    private String newPassword;
    private String confirmPassword;
    private String email;
    private String role;

    public UserForm(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
