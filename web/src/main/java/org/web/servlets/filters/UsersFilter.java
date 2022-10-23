package org.web.servlets.filters;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter(servletNames = {"UsersServlet"})
public class UsersFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String idKey = request.getParameter("idUser");
        UserDAO userDAO = new UserDAOImpl();
        if (idKey != null) {
            userDAO.delete(Long.parseLong(idKey));
        }
        if (userDAO.getListUsers().size() == 0) {
            request.setAttribute("userNull", "Sorry your not lucky");
        }
        request.setAttribute("list", userDAO.getListUsers());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
