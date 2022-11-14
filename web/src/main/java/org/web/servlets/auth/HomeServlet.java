package org.web.servlets.auth;

import entity.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebServlet(name = "HomeServlet",
        urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String pass = req.getParameter("password");
        User user;
        user = (User) session.getAttribute("user");
        if (user.getUserName() != null && user.getPassword() != null && user.getPassword().equals(pass)) {
            ServletContext context = getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher("/welcome");
            dispatcher.forward(req, resp);
        } else {
            session.removeAttribute("user");
            req.setAttribute("registration", "Sorry user or password not true");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
