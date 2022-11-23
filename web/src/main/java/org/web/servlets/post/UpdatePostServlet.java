package org.web.servlets.post;

import dao.PostDAO;
import dao.TopicDAO;
import dao.impl.PostDAOImpl;
import dao.impl.TopicDAOImpl;
import entity.Post;
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

@WebServlet(name = "UpdatePostServlet",
        urlPatterns = {"/updatePost"})
public class UpdatePostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("idTopic", req.getParameter("idTopic"));
        session.setAttribute("idPost", req.getParameter("idPost"));
        if (session.getAttribute("name") != null) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/post/updatePost.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        TopicDAO topicDAO = new TopicDAOImpl();
        PostDAO postDAO = new PostDAOImpl();
        String idTopic = (String) session.getAttribute("idTopic");
        String idPost = (String) session.getAttribute("idPost");
        try {
            if (session.getAttribute("user") != null) {
                Post post = new Post(req.getParameter("updateName"), req.getParameter("updateTextPost"));
                post.setId(Long.parseLong(idPost));
                post.setUser(user);
                post.setTopic(topicDAO.get(Long.parseLong(idTopic)));
                postDAO.update(post);
                session.removeAttribute("idPost");
            }
        } catch (LoginException e) {
            String error = String.valueOf(e.getMessage());
            req.setAttribute("error", error);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/post/updatePost.jsp");
            requestDispatcher.forward(req, resp);
        } catch (CatchingCauseException e) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/auth/error.jsp");
            requestDispatcher.forward(req, resp);
        }
        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/post");
        dispatcher.forward(req, resp);
    }
}
