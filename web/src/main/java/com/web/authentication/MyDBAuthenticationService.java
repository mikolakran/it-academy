package com.web.authentication;

import com.web.facades.UserFacade;
import com.web.forms.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class MyDBAuthenticationService implements UserDetailsService {

    @Autowired
    private UserFacade userFacade;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserForm userForm = userFacade.getByName(username);
        if (userForm == null) {
            throw new UsernameNotFoundException("User " //
                    + username + " was not found in the database");
        }

        String role = userForm.getRole().toUpperCase();

        List<GrantedAuthority> grantList = new ArrayList<>();

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

        grantList.add(authority);

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new User(userForm.getUserName(),
                userForm.getPassword(), enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, grantList);
    }
}