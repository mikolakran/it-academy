package org.example.registration.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.example.registration.check.CheckUser;
import org.example.registration.inter.CheckUserInterface;
import org.example.registration.inter.exception.LoginException;

import java.io.IOException;

@WebFilter(servletNames = {"RegistrationServlet"})
public class FilterRegistration implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        CheckUserInterface checkUser = new CheckUser();
        try {
            checkUser.isValidationEmail(request.getParameter("email"));
        } catch (LoginException e) {
            String error = String.valueOf(e.getMessage());
            request.setAttribute("error", error);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
