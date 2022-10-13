package org.example.registration.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;


import java.io.IOException;
@WebFilter(servletNames = {"HelloServlet"})
public class FilterHelloServlet implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
