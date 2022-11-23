package org.web.servlets.filters.auth;

import dao.TopicDAO;
import dao.UserDAO;
import dao.impl.TopicDAOImpl;
import dao.impl.UserDAOImpl;
import entity.User;
import exception.CatchingCauseException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(servletNames = {"HomeServlet", "UsersServlet", "WelcomeServlet"})
public class DisplayArrayListFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        TopicDAO topicDAO = new TopicDAOImpl();
        UserDAO userDAO = new UserDAOImpl();
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("name") != null) {
                String deleteTopic = request.getParameter("deleteTopic");
                String idUserTopic = request.getParameter("idUserTopic");
                User user = (User) session.getAttribute("user");
                if (session.getAttribute("idTopic") != null) {
                    session.removeAttribute("idTopic");
                }
                if (idUserTopic != null) {
                    topicDAO.deleteTopic(user.getId(), Long.parseLong(idUserTopic));
                }
                if (deleteTopic != null) {
                    topicDAO.delete(Long.parseLong(deleteTopic));
                }
                if (topicDAO.getListTopic(user.getId()).size() == 0) {
                    request.setAttribute("topicNull", "Topics null");
                } else {
                    request.setAttribute("topicList", topicDAO.getListTopic(user.getId()));
                }

                String idKey = request.getParameter("idUser");
                if (idKey != null) {
                    userDAO.delete(Long.parseLong(idKey));
                }
                if (userDAO.getListUsers().size() == 0) {
                    request.setAttribute("userNull", "Sorry your not lucky");
                } else {
                    request.setAttribute("list", userDAO.getListUsers());
                }
            }
        } catch (CatchingCauseException e) {
            RequestDispatcher requestDispatcher = servletRequest.getRequestDispatcher("WEB-INF/auth/error.jsp");
            requestDispatcher.forward(request, servletResponse);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
