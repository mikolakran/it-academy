package org.example.registration.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.example.registration.check.ChangesUser;
import org.example.registration.inter.ChangesUserInterface;
import org.example.registration.inter.ReadingUser;
import org.example.registration.json.GetUser;

import java.io.IOException;


@WebFilter(servletNames = {"Users"})
public class FilterUsers implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String idKey1 = request.getParameter("id");
        ChangesUserInterface changesUser = new ChangesUser();
        if (idKey1!=null) {
            changesUser.deleteUserByKey(Integer.parseInt(idKey1));
        }
        ReadingUser readingUser = new GetUser();
        request.setAttribute("list", readingUser.getListUsers());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
