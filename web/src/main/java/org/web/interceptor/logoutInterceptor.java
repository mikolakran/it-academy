package org.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.web.servlet.mvc.WebContentInterceptor;


public class logoutInterceptor extends WebContentInterceptor {

    @SneakyThrows
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession(false);
        if (session.getAttribute("userSession")==null) {
            response.sendRedirect(request.getContextPath()+"/login");
        }
        return true;
    }

}
