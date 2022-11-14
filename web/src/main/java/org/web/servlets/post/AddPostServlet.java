package org.web.servlets.post;

import dao.TopicDAO;
import dao.impl.PostDAOImpl;
import dao.impl.TopicDAOImpl;
import entity.Post;
import entity.User;
import exception.CatchingCauseException;
import exception.LoginException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "AddPostServlet",
        urlPatterns = {"/addPost"})
public class AddPostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("name") != null) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/post/addPost.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String idTopic = (String) session.getAttribute("idTopic");
        User user = (User) session.getAttribute("user");
        TopicDAO topicDAO = new TopicDAOImpl();
        PostDAOImpl postDAO = new PostDAOImpl();
        Post post = new Post(req.getParameter("postName"), req.getParameter("textPost"));
        try {
            if (session.getAttribute("user") != null) {
                post.setTopic(topicDAO.get(Long.parseLong(idTopic)));
                post.setUser(user);
                postDAO.save(post);
                req.setAttribute("getPostList", postDAO.
                        getListByIdUserPost(Long.parseLong(idTopic), user.getId()));
            }
        } catch (LoginException e) {
            String error = String.valueOf(e.getMessage());
            req.setAttribute("error", error);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/post/addPost.jsp");
            requestDispatcher.forward(req, resp);
        } catch (CatchingCauseException e) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/auth/error.jsp");
            requestDispatcher.forward(req, resp);
        }
        post.setUser(user);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/post/post.jsp");
        requestDispatcher.forward(req, resp);
    }
}
