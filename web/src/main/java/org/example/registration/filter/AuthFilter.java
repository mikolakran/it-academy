package org.example.registration.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.registration.check.CheckUser;
import org.example.registration.inter.CheckUserInterface;
import org.example.registration.inter.ReadingUser;
import org.example.registration.inter.exception.LoginException;
import org.example.registration.json.GetUser;
import org.example.registration.user.User;

import java.io.IOException;


@WebFilter(servletNames = {"Home", "RegistrationServlet"})
public class AuthFilter implements Filter {
    private User user;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        CheckUserInterface checkUser = new CheckUser();
        ReadingUser readingUser = new GetUser();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        isUserInJson(checkUser, request);
        HttpSession session = request.getSession();
        String pass = request.getParameter("password");
        Object s = session.getAttribute("user");
        if (s != null) {
            if (session.getAttribute("userName") != null && session.getAttribute("password") != null) {
                session.getAttribute("role");
            } else {
                if (user != null) {
                    request.getSession().setAttribute("userName", user.getUserName());
                    request.getSession().setAttribute("password", user.getPassword());
                    request.getSession().setAttribute("email", user.getEmail());
                    request.getSession().setAttribute("role", user.getRole());
                }
            }
        } else {
            if (user.getUserName() != null && user.getPassword() != null) {
                user = readingUser.getUserByKeyTableName(user.getUserName());
                if (user.getUserName() != null && user.getPassword() != null&&user.getPassword().equals(pass)){
                    request.getSession().setAttribute("user", user);
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void isUserInJson(CheckUserInterface checkUser, HttpServletRequest request) {
        user = new User();
        user.setUserName(request.getParameter("userName"));
        user.setPassword(request.getParameter("password"));
        String pass = request.getParameter("password2");
        user.setEmail(request.getParameter("email"));
        try {
            checkUser.isPassAndPass(user.getPassword(), pass);
            checkUser.isValidationUserName(user.getUserName());
            checkUser.isValidationPassword(user.getPassword());
        } catch (LoginException e) {
            String error = String.valueOf(e.getMessage());
            request.setAttribute("error", error);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
