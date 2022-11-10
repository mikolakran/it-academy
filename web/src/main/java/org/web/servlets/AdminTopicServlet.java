package org.web.servlets;

import dao.TopicDAO;
import dao.impl.TopicDAOImpl;
import entity.Topic;
import entity.User;
import exception.CatchingCauseException;
import exception.LoginException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebServlet(name = "AdminTopicServlet",
        urlPatterns = {"/adminTopic"})
public class AdminTopicServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/adminTopic.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TopicDAO topicDAO = new TopicDAOImpl();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        Set<User> users = new HashSet<>();
        users.add(user);
        Topic topic = new Topic(req.getParameter("adminTopic"));
        topic.setUsers(users);
        try {
            topicDAO.update(topic);
            req.setAttribute("topicList", topicDAO.getListTopic());
        } catch (LoginException e) {
            String error = String.valueOf(e.getMessage());
            req.setAttribute("error", error);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/adminTopic.jsp");
            requestDispatcher.forward(req, resp);
        } catch (CatchingCauseException e) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/error.jsp");
            requestDispatcher.forward(req, resp);
        }
        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/welcome");
        dispatcher.forward(req, resp);
    }
}
