package org.web.servlets;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import entity.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "WelcomeServlet",
        urlPatterns = {"/welcome"})
public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            setNameAndRoleUser(req, user);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/welcome.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        setNameAndRoleUser(req, user);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/welcome.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void setNameAndRoleUser(HttpServletRequest req, User user) {
        UserDAO userDAO = new UserDAOImpl();
        req.setAttribute("name", user.getUserName());
        req.setAttribute("role", userDAO.getRole(user));
    }
}
