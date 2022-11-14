package org.web.servlets.filters.topic;

import dao.TopicDAO;
import dao.UserDAO;
import dao.impl.TopicDAOImpl;
import dao.impl.UserDAOImpl;
import entity.Topic;
import entity.User;
import exception.CatchingCauseException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter(servletNames = {"AddTopicServlet"})
public class AddTopicFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        TopicDAO topicDAO = new TopicDAOImpl();
        UserDAO userDAO = new UserDAOImpl();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        try {
            if (session.getAttribute("user") != null) {
                User userAdmin = userDAO.getRoleData("admin");
                Set<Topic> listAdminTopic = topicDAO.getListTopic(userAdmin.getId());
                Set<Topic> listUserTopic = topicDAO.getListTopic(user.getId());
                Set<Topic> topics = new HashSet<>();
                listAdminTopic.forEach(topic -> {
                    if (!listUserTopic.contains(topic)) {
                        topics.add(topic);
                    }
                });
                if (topics.size() == 0) {
                    request.setAttribute("topicNull", "Topics null");
                } else {
                    request.setAttribute("topicList", topics);
                }
            }
        } catch (CatchingCauseException e) {
            RequestDispatcher requestDispatcher = servletRequest.getRequestDispatcher("WEB-INF/auth/error.jsp");
            requestDispatcher.forward(request, servletResponse);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
