package org.web.filters;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web.facades.TopicFacade;
import org.web.forms.TopicForm;
import org.web.forms.UserForm;

import java.io.IOException;
import java.util.Set;

@Component("welcomesFilter")
public class WelcomeFilter implements Filter {

    @Autowired
    private TopicFacade topicFacade;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession(false);
        UserForm userForm = (UserForm) session.getAttribute("userSession");
        if (userForm != null) {
            Set<TopicForm> listTopic = topicFacade.getListTopic(userForm.getId());
            if (listTopic.size() != 0) {
                session.setAttribute("topics", listTopic);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
