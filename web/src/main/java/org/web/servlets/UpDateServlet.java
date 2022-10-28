package org.web.servlets;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import entity.User;
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

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UpDateServlet",
        urlPatterns = "/upDate")
public class UpDateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idUser = req.getParameter("idUser");
        HttpSession session = req.getSession();
        if (idUser != null && session.getAttribute("name") != null) {
            long id = Long.parseLong(idUser);
            session.setAttribute("id", id);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/upDate.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO userDAO = new UserDAOImpl();
        HttpSession session = req.getSession();
        User userSession = (User) session.getAttribute("user");
        User userSQLData = null;
        String name = req.getParameter("userName");
        String email = req.getParameter("email");
        String pass = req.getParameter("password");
        String pass2 = req.getParameter("password2");
        try {
            userSQLData = userDAO.get((Long) session.getAttribute("id"));
        } catch (PropertyVetoException | SQLException | LoginException e) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/error.jsp");
            requestDispatcher.forward(req, resp);
        }
        if (req.getAttribute("error") == null) {
            try {
                assert userSQLData != null;
                updateDataByUser(userDAO, name, email, pass, pass2, userSQLData);
                setRole(userDAO, userSQLData, userSession, session);
                session.removeAttribute("id");
                ServletContext context = getServletContext();
                RequestDispatcher dispatcher = context.getRequestDispatcher("/welcome");
                dispatcher.forward(req, resp);
            } catch (LoginException e) {
                String error = String.valueOf(e.getMessage());
                req.setAttribute("error", error);
                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/upDate.jsp");
                rd.forward(req, resp);
            } catch (PropertyVetoException | SQLException e) {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/error.jsp");
                requestDispatcher.forward(req, resp);
            }
        } else {
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/upDate.jsp");
            rd.forward(req, resp);
        }
    }

    private void updateDataByUser(UserDAO userDAO, String name, String email,
                                  String pass, String pass2, User userSQLData)
            throws LoginException, PropertyVetoException, SQLException {
        if (!userSQLData.getUserName().equals(name)) {
            userSQLData.setUserName(name);
            userDAO.update(userSQLData.getId(), "name", name);
        }
        if (!pass2.equals("")) {
            if (!userSQLData.getPassword().equals(pass2)) {
                ValidationAuth checkUserInterface = new CheckUser();
                checkUserInterface.validationPassword(pass2);
                userSQLData.setPassword(pass2);
                userDAO.update(userSQLData.getId(), "password", pass2);
            }
        } else {
            if (!userSQLData.getPassword().equals(pass)) {
                userSQLData.setPassword(pass);
                userDAO.update(userSQLData.getId(), "password", pass);
            }
        }
        if (!userSQLData.getEmail().equals(email)) {
            userSQLData.setEmail(email);
            userDAO.update(userSQLData.getId(), "email", email);
        }
    }

    private void setRole(UserDAO userDAO, User userSQLData, User userSession, HttpSession session)
            throws LoginException, PropertyVetoException, SQLException {
        if (userDAO.getRole(userSQLData).equals("admin") ||
                userDAO.getRole(userSession).equals("admin")) {
            if (userSession.getId() == (userSQLData.getId())) {
                userDAO.update(userSQLData.getId(), "role", userDAO.getRole(userSQLData));
                userSQLData = userDAO.get(userSQLData.getId());
                session.removeAttribute("user");
                session.setAttribute("user", userSQLData);
            } else {
                session.removeAttribute("user");
                session.setAttribute("user", userSession);
            }
        } else {
            session.removeAttribute("user");
            session.setAttribute("user", userSQLData);
        }
    }

}
