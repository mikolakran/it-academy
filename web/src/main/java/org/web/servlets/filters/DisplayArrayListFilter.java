package org.web.servlets.filters;

import dao.TopicDAO;
import dao.UserDAO;
import dao.impl.TopicDAOImpl;
import dao.impl.UserDAOImpl;
import exception.CatchingCauseException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
@WebFilter(servletNames = {"HomeServlet","TopicServlet","UsersServlet","WelcomeServlet"})
public class DisplayArrayListFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        TopicDAO topicDAO = new TopicDAOImpl();
        try {
            String idAdminTopic = request.getParameter("idAdminTopic");
            String idUserTopic = request.getParameter("idUserTopic");

            if (idAdminTopic != null) {
                topicDAO.delete(Long.parseLong(idAdminTopic));
            }
            if (topicDAO.getListTopic().size() == 0){
                request.setAttribute("topicNull", "Topics null");
            }else {
                request.setAttribute("topicList", topicDAO.getListTopic());
            }

            String idKey = request.getParameter("idUser");
            UserDAO userDAO = new UserDAOImpl();
                if (idKey != null) {
                    userDAO.delete(Long.parseLong(idKey));
                }
                if (userDAO.getListUsers().size() == 0) {
                    request.setAttribute("userNull", "Sorry your not lucky");
                }else {
                    request.setAttribute("list", userDAO.getListUsers());
                }
        } catch (CatchingCauseException e) {
            RequestDispatcher requestDispatcher = servletRequest.getRequestDispatcher("WEB-INF/error.jsp");
            requestDispatcher.forward(request, servletResponse);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
