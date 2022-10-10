package org.example.registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.registration.inter.exception.LoginException;
import org.example.registration.inter.WriteFileUser;
import org.example.registration.json.RecordUser;
import org.example.registration.properties.PropertiesFileRegistration;
import org.example.registration.user.User;

import java.io.IOException;

@WebServlet(name = "RegistrationServlet",
        urlPatterns = {"/registration"})
public class RegistrationServlet extends HttpServlet {
    private int count = 1;
    private String role;
    private final WriteFileUser writeFileUser = new RecordUser(PropertiesFileRegistration.getProperties());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/add.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        HttpSession session = req.getSession();
        if (req.getAttribute("error") == null) {
            if (email.equals("mikolakran@gmail.com")) { //my admin email
                setRole("admin");
                req.setAttribute("role", getRole());
            } else {
                setRole("user");
            }
            User user = new User(count, req.getParameter("userName"),
                    req.getParameter("password"), req.getParameter("email"), getRole());
            if (user.getUserName() != null && user.getPassword() != null &&
                    user.getEmail() != null) {
                count++;
            }
            try {
                writeFileUser.writeUser(user);
                session.setAttribute("user", user);
                req.setAttribute("userName", user.getUserName());
            } catch (LoginException ignored) {
            }
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/welcome.jsp");
            requestDispatcher.forward(req, resp);
        } else {
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
