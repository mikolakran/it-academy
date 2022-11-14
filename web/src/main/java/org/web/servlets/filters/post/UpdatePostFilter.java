package org.web.servlets.filters.post;

import dao.PostDAO;
import dao.impl.PostDAOImpl;
import entity.Post;
import exception.CatchingCauseException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter(servletNames = {"UpdatePostServlet"})
public class UpdatePostFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        PostDAO postDAO = new PostDAOImpl();
        String idPost = request.getParameter("idPost");
        try {
            if (idPost != null) {
                Post post = postDAO.get(Long.parseLong(idPost));
                request.setAttribute("updateName", post.getName());
                request.setAttribute("updateTextPost", post.getText());
            }
        } catch (CatchingCauseException e) {
            RequestDispatcher requestDispatcher = servletRequest.getRequestDispatcher("WEB-INF/auth/error.jsp");
            requestDispatcher.forward(servletRequest, servletResponse);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
