package org.example.registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.registration.user.User;

import java.io.IOException;

@WebServlet(name = "Home",
        urlPatterns = {"/home"})
public class Home extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user;
        user = (User) session.getAttribute("user");
        if (user != null&&req.getAttribute("error")==null) {
            session.setAttribute("userName", user.getUserName());
            session.setAttribute("role", user.getRole());
            ServletContext context = getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher("/hello");
            dispatcher.forward(req, resp);
        } else {
            session.removeAttribute("user");
            req.setAttribute("registration", "Sorry user or password not true");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
