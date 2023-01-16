package com.web.filters;


import com.web.facades.TopicFacade;
import com.web.forms.TopicForm;
import com.web.forms.UserForm;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Set;

@Component
public class WelcomeFilter implements Filter {

    private TopicFacade topicFacade;

    public WelcomeFilter(TopicFacade topicFacade) {
        this.topicFacade = topicFacade;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession(false);
        if(session != null) {
            UserForm userForm = (UserForm) session.getAttribute("userSession");
            session.removeAttribute("userAndAdmin");
            String deleteIdTopic = request.getParameter("deleteIdTopic");
            if (userForm != null) {
                if ( deleteIdTopic != null) {
                    topicFacade.deleteUserAndPost(userForm.getId(), Long.parseLong(deleteIdTopic));
                }
                Set<TopicForm> listTopic = topicFacade.getListTopic(userForm.getId());
                if (listTopic.size() != 0) {
                    session.setAttribute("topics", listTopic);
                }else {
                    session.removeAttribute("topics");
                }
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

}
