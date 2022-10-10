package org.example.registration.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.example.registration.check.ChangesUser;
import org.example.registration.inter.ChangesUserInterface;
import org.example.registration.inter.ReadingUser;
import org.example.registration.json.GetUser;
import org.example.registration.properties.PropertiesFileRegistration;
import org.example.registration.user.User;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(servletNames = {"Users"})
public class FilterUsers implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        List<User> list = new ArrayList<>();
        String idKey1 = request.getParameter("id");
        ChangesUserInterface changesUser = new ChangesUser(PropertiesFileRegistration.getProperties());
        changesUser.deleteUserByKey(idKey1, PropertiesFileRegistration.getProperties());
        ReadingUser readingUser = new GetUser(PropertiesFileRegistration.getProperties());
        JSONObject allUser = readingUser.getAllUser();
        JSONObject jsonObject = (JSONObject) allUser.get("people");
        for (int i = 0; i < 100; i++) {
            String idKey = String.valueOf(i);
            JSONObject jsonObject1 = (JSONObject) jsonObject.get(idKey);
            if (jsonObject1 != null) {
                if (readingUser.getUserByKeyTable(idKey, "role").equals("user")) {
                    User user = new User();
                    user.setId((Long) jsonObject1.get("id"));
                    user.setUserName((String) jsonObject1.get("userName"));
                    user.setPassword((String) jsonObject1.get("password"));
                    user.setEmail((String) jsonObject1.get("email"));
                    user.setRole((String) jsonObject1.get("role"));
                    list.add(user);
                }
            }
            request.setAttribute("list", list);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
