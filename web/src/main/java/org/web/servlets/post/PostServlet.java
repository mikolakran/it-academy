package org.web.servlets.post;

import dao.PostDAO;
import dao.impl.PostDAOImpl;
import entity.User;
import exception.CatchingCauseException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "PostsServlet",
        urlPatterns = {"/post"})
public class PostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        req.setAttribute("idTopic", req.getParameter("idTopic"));
        if (session.getAttribute("idTopic") != null && session.getAttribute("name") != null) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/post/post.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        PostDAO postDAO = new PostDAOImpl();
        String idTopic = (String) session.getAttribute("idTopic");
        User idUser = (User) session.getAttribute("user");
        try {
            req.setAttribute("getPostList", postDAO.
                    getListByIdUserPost(Long.parseLong(idTopic), idUser.getId()));
        } catch (CatchingCauseException e) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/auth/error.jsp");
            requestDispatcher.forward(req, resp);
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/post/post.jsp");
        requestDispatcher.forward(req, resp);
    }
}
