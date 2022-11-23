package org.web.servlets.auth;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "UsersServlet",
        urlPatterns = "/users")
public class UsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("name") == null) {
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/auth/users.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
