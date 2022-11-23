package org.web.servlets.auth;

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
            setNameAndRoleUser(req, resp, user);
        } else {
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        setNameAndRoleUser(req, resp, user);
    }

    private void setNameAndRoleUser(HttpServletRequest req,
                                    HttpServletResponse resp, User user) throws ServletException, IOException {
        UserDAO userDAO = new UserDAOImpl();
        req.setAttribute("name", user.getUserName());
        req.setAttribute("role", userDAO.getRole(user));
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/auth/welcome.jsp");
        requestDispatcher.forward(req, resp);
    }
}
