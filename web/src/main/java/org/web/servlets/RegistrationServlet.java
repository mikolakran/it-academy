package org.web.servlets;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
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
import validation.CheckUser;
import validation.ValidationAuth;

import java.io.IOException;

@WebServlet(name = "RegistrationServlet",
        urlPatterns = {"/registration"})
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/add.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = new User();
        ValidationAuth checkUser = new CheckUser();
        UserDAO userDAO = new UserDAOImpl();
        user.setUserName(req.getParameter("userName"));
        user.setPassword(req.getParameter("password"));
        user.setEmail(req.getParameter("email"));
        if (req.getAttribute("error") == null) {
            user.setRole(userDAO.getRole(user));
            String password2 = req.getParameter("password2");
            try {
                checkUser.validationPassSamePass2(user.getPassword(), password2);
                userDAO.save(user);
                session.setAttribute("user", userDAO.getByName(user.getUserName()));
                session.setAttribute("name", user.getUserName());
            } catch (LoginException e) {
                String error = String.valueOf(e.getMessage());
                req.setAttribute("error", error);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/add.jsp");
                requestDispatcher.forward(req, resp);
            } catch (CatchingCauseException e) {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/error.jsp");
                requestDispatcher.forward(req, resp);
            }
            ServletContext context = getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher("/welcome");
            dispatcher.forward(req, resp);
        } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/add.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
