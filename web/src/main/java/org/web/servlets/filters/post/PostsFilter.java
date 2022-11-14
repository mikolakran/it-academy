package org.web.servlets.filters.post;

import dao.PostDAO;
import dao.impl.PostDAOImpl;
import entity.User;
import exception.CatchingCauseException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(servletNames = {"PostsServlet"})
public class PostsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        if (session.getAttribute("idTopic") == null) {
            session.setAttribute("idTopic", request.getParameter("idTopic"));
        }
        PostDAO postDAO = new PostDAOImpl();
        User idUser = (User) session.getAttribute("user");
        String deletePost = request.getParameter("deletePost");
        String idTopic = (String) session.getAttribute("idTopic");
        try {
            if (deletePost != null) {
                postDAO.delete(Long.parseLong(deletePost));
            }
            if (idTopic != null) {
                if (postDAO.getListByIdUserPost(Long.parseLong(idTopic),
                        idUser.getId()).size() != 0) {
                    request.setAttribute("getPostList", postDAO.getListByIdUserPost(Long.parseLong(idTopic),
                            idUser.getId()));
                } else {
                    request.setAttribute("postNull", "Post null");
                }
            } else {
                request.setAttribute("postNull", "Post null");
            }
        } catch (CatchingCauseException e) {
            RequestDispatcher requestDispatcher = servletRequest.getRequestDispatcher("WEB-INF/auth/error.jsp");
            requestDispatcher.forward(servletRequest, servletResponse);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
