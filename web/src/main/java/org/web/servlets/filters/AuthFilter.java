package org.web.servlets.filters;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import entity.User;
import exception.LoginException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import validation.CheckUser;
import validation.ValidationAuth;

import java.io.IOException;

@WebFilter(servletNames = {"HomeServlet",
        "RegistrationServlet",
        "UpDateServlet"})
public class AuthFilter implements Filter {
    private User user = null;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        UserDAO userDAO = new UserDAOImpl();
        ValidationAuth checkUser = new CheckUser();
        user = (User) session.getAttribute("user");
        if (session.getAttribute("user") == null || user.getId() == 0) {
            isValidation(request, checkUser);
            user = userDAO.getByName(user.getUserName());
            if (user != null) {
                session.setAttribute("user", user);
                session.setAttribute("name", user.getUserName());
            }
        } else {
            isValidation(request, checkUser);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void isValidation(HttpServletRequest request, ValidationAuth checkUser) {
        user = new User();
        user.setUserName(request.getParameter("userName"));
        user.setPassword(request.getParameter("password"));
        user.setEmail(request.getParameter("email"));
        try {
            checkUser.validationName(user.getUserName());
            checkUser.validationPassword(user.getPassword());
            checkUser.validationEmail(user.getEmail());
        } catch (LoginException e) {
            String error = String.valueOf(e.getMessage());
            request.setAttribute("error", error);
        }
    }

}
