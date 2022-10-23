package org.web.servlets.filters;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(servletNames = {"UpDateServlet"})
public class UpDateFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        UserDAO userDAO = new UserDAOImpl();
        String idKey = request.getParameter("idUser");
        User user = null;
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("user");
        if (idKey != null && session.getAttribute("name") != null) {
            user = userDAO.get(Long.parseLong(idKey));
            if (!sessionUser.getUserName().equals(user.getUserName()) ||
                    user.getRole().equals("admin")) {
                user.setRole(sessionUser.getRole());
                request.setAttribute("role", user.getRole());
            }
        } else {
            if (session.getAttribute("name") != null) {
                if (session.getAttribute("id") != null) {
                    user = (User) session.getAttribute("user");
                    user = userDAO.get(user.getId());
                    long idSession = (long) session.getAttribute("id");
                    if (user.getId() == idSession) {
                        request.setAttribute("role", userDAO.getRole(user));
                    } else {
                        request.setAttribute("role", userDAO.getRole(user));
                        user = userDAO.get((Long) session.getAttribute("id"));
                    }
                } else {
                    user = (User) session.getAttribute("user");
                    user = userDAO.get(user.getId());
                    request.setAttribute("role", userDAO.getRole(user));
                }

            }
        }
        if (user != null) {
            request.setAttribute("name", user.getUserName());
            request.setAttribute("pass", user.getPassword());
            request.setAttribute("emailName", user.getEmail());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
