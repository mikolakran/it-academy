package org.example.registration.inter;


import org.example.registration.inter.exception.LoginException;
import org.example.registration.user.User;

public interface WriteFileUser {
    void writeUser(User user) throws LoginException;
}
