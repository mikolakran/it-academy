package org.example.registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.registration.check.ChangesUser;
import org.example.registration.check.CheckUser;
import org.example.registration.inter.ChangesUserInterface;
import org.example.registration.inter.CheckUserInterface;
import org.example.registration.inter.ReadingUser;
import org.example.registration.inter.exception.LoginException;
import org.example.registration.json.GetUser;
import org.example.registration.user.User;

import java.io.IOException;
@WebServlet(name = "UpDate",
        urlPatterns = "/upDate")
public class UpDate extends HttpServlet {
    private String idKey = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        idKey = req.getParameter("id");
        HttpSession session = req.getSession();
        session.setAttribute("idName",idKey);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/upDate.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ChangesUserInterface changesUser = new ChangesUser();
        ReadingUser readingUser = new GetUser();
        CheckUserInterface checkUser = new CheckUser();
        HttpSession session = req.getSession();
        User user1 = (User) session.getAttribute("user");
        String id = (String) session.getAttribute("idName");
        if (id!=null){
            idKey = id;
        }
        String name = req.getParameter("userName");
        String email = req.getParameter("email");
        String pass = req.getParameter("password");
        String pass2 = req.getParameter("password2");
        User user ;
        user = readingUser.getUserByKey(Integer.parseInt(idKey));
        if (user !=null){
            try {
                if (!user.getUserName().equals(name)){
                    checkUser.isValidationUserName(name);
                    user.setUserName(name);
                    checkUser.isExistUserName(user);
                    changesUser.changesBySelectTable(user.getId(),"name", name);
                }
                if (!pass2.equals("")){
                    if (!user.getPassword().equals(pass2)){
                        checkUser.isValidationPassword(pass2);
                        user.setPassword(pass2);
                        changesUser.changesBySelectTable(Integer.parseInt(idKey),"password", pass2);
                    }
                }else {
                    if (!user.getPassword().equals(pass)){
                        checkUser.isValidationPassword(pass);
                        user.setPassword(pass);
                        changesUser.changesBySelectTable(Integer.parseInt(idKey),"password", pass);
                    }
                }
                if (!user.getEmail().equals(email)){
                    checkUser.isValidationEmail(email);
                    user.setEmail(email);
                    checkUser.isExistUserEmail(user);
                    changesUser.changesBySelectTable(Integer.parseInt(idKey),"email", email);
                }
            } catch (LoginException e) {
                String error = String.valueOf(e.getMessage());
                req.setAttribute("error", error);
                req.setAttribute("name", name);
                req.setAttribute("pass", pass);
                req.setAttribute("emailName", email);
                RequestDispatcher rd= req.getRequestDispatcher("WEB-INF/upDate.jsp");
                rd.forward(req, resp);
            }
        }
        if (user1!=null&&user!=null){
            if (user1.getRole().equals("admin")) {
                if (user1.getId()==(user.getId())) {
                    session.setAttribute("idUserName", name);
                } else {
                    user1 = readingUser.getUserByKey( user1.getId());
                    session.setAttribute("idUserName", user1.getUserName());
                }
            }else {
                session.setAttribute("idUserName",name);
            }
        }
        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/hello");
        dispatcher.forward(req,resp);
    }

}
