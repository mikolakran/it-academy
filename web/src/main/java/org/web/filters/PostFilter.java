package org.web.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web.facades.PostFacade;
import org.web.forms.PostForm;
import org.web.forms.UserForm;

import java.io.IOException;
import java.util.Set;

@Component
public class PostFilter implements Filter {

    @Autowired
    private PostFacade postFacade;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession(false);
        UserForm userForm = (UserForm) session.getAttribute("userSession");
        if (userForm != null) {
            String idTopic = request.getParameter("idTopic");
            String deletePost = request.getParameter("deletePost");
            if (deletePost != null) {
                postFacade.delete(Long.valueOf(deletePost));
            }
            request.setAttribute("idTopic", idTopic);
            Set<PostForm> listPost = postFacade.getListByIdUserPost(Long.parseLong(idTopic), userForm.getId());
            session.setAttribute("posts", listPost);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
