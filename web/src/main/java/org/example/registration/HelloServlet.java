package org.example.registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.registration.user.User;

import java.io.IOException;

@WebServlet(name = "HelloServlet",
        urlPatterns = {"/hello"})
public class HelloServlet extends HttpServlet {
    private User user;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            req.setAttribute("role", user.getRole());
            session.getAttribute("userName");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/welcome.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        user = new User();
        HttpSession session = req.getSession();
        String id = (String) session.getAttribute("idUserName");
        if (id!=null){
            session.setAttribute("userName",id);
        }else {
            user = (User) session.getAttribute("user");
            session.setAttribute("userName", user.getUserName());
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/welcome.jsp");
        requestDispatcher.forward(req, resp);
    }
}
