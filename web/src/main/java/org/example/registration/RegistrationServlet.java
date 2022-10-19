package org.example.registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.registration.inter.ReadingUser;
import org.example.registration.inter.exception.LoginException;
import org.example.registration.inter.WriteFileUser;
import org.example.registration.json.GetUser;
import org.example.registration.json.RecordUser;
import org.example.registration.user.User;

import java.io.IOException;

@WebServlet(name = "RegistrationServlet",
        urlPatterns = {"/registration"})
public class RegistrationServlet extends HttpServlet {
    private String role;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/add.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        HttpSession session = req.getSession();
        ReadingUser readingUser = new GetUser();
        WriteFileUser writeFileUser = new RecordUser();
            if (email.equals("mikolakran@gmail.com")) { //my admin email
                setRole("admin");
                req.setAttribute("role", getRole());
            } else {
                setRole("user");
            }
            User user = new User(req.getParameter("userName"),
                    req.getParameter("password"), req.getParameter("email"), getRole());
        if (req.getAttribute("error") == null) {
            try {
                writeFileUser.writeUser(user);
                user = readingUser.getUserByKeyTableName(user.getUserName());
                session.setAttribute("user", user);
                req.setAttribute("userName", user.getUserName());
                ServletContext context = getServletContext();
                RequestDispatcher dispatcher = context.getRequestDispatcher("/hello");
                dispatcher.forward(req, resp);
            } catch (LoginException e) {
                String error = String.valueOf(e.getMessage());
                req.setAttribute("error", error);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/add.jsp");
                requestDispatcher.forward(req, resp);
            }
        }else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/add.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    private String getRole() {
        return role;
    }

    private void setRole(String role) {
        this.role = role;
    }
}
