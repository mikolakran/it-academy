package org.web.servlets.topic;

import dao.TopicDAO;
import dao.UserDAO;
import dao.impl.TopicDAOImpl;
import dao.impl.UserDAOImpl;
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
import java.util.Set;

@WebServlet(name = "AddTopicServlet",
        urlPatterns = {"/addTopic"})
public class AddTopicServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null && session.getAttribute("name") != null) {
            req.setAttribute("role", user.getRole());
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/topic/addTopic.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/home");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TopicDAO topicDAO = new TopicDAOImpl();
        UserDAO userDAO = new UserDAOImpl();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        try {
            Set<Topic> topics = topicDAO.getListTopic(user.getId());
            if (req.getParameter("idUserTopic") != null) {
                Topic topic = topicDAO.get(Long.parseLong(req.getParameter("idUserTopic")));
                topics.add(topic);
                user.setTopic(topics);
                userDAO.update(user);
                req.setAttribute("topicList", topicDAO.getListTopic(user.getId()));
                req.setAttribute("topicNull", "All Topic");
            }
            if (req.getParameter("addTopic") != null) {
                Topic topicAdmin = new Topic(req.getParameter("addTopic"));
                topics.add(topicAdmin);
                user.setTopic(topics);
                userDAO.update(user);
                req.setAttribute("topicList", topicDAO.getListTopic(user.getId()));
                req.setAttribute("topicNull", "All Topic");
            }
        } catch (LoginException e) {
            String error = String.valueOf(e.getMessage());
            req.setAttribute("error", error);
            req.setAttribute("role", user.getRole());
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/topic/addTopic.jsp");
            requestDispatcher.forward(req, resp);
        } catch (CatchingCauseException e) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/auth/error.jsp");
            requestDispatcher.forward(req, resp);
        }
        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/welcome");
        dispatcher.forward(req, resp);
    }
}
