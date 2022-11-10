package org.web.servlets.filters;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import exception.CatchingCauseException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;


public class UsersFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String idKey = request.getParameter("idUser");
        UserDAO userDAO = new UserDAOImpl();
        try {
            if (idKey != null) {
                userDAO.delete(Long.parseLong(idKey));
            }
            if (userDAO.getListUsers().size() == 0) {
                request.setAttribute("userNull", "Sorry your not lucky");
            }
            request.setAttribute("list", userDAO.getListUsers());
        } catch (CatchingCauseException e) {
            RequestDispatcher requestDispatcher = servletRequest.getRequestDispatcher("WEB-INF/error.jsp");
            requestDispatcher.forward(servletRequest, servletResponse);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
