package com.web.filters;

import com.web.facades.PostFacade;
import com.web.forms.PostForm;
import com.web.forms.UserForm;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Set;

@Component
public class PostFilter implements Filter {

    private PostFacade postFacade;

    public PostFilter(PostFacade postFacade) {
        this.postFacade = postFacade;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession(false);
        if(session != null) {
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
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
