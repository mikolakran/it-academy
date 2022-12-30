package org.web.controller.post;

import exception.MyException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.web.facades.PostFacade;
import org.web.facades.TopicFacade;
import org.web.forms.PostForm;
import org.web.forms.TopicForm;
import org.web.forms.UserForm;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Controller
public class PostController {

    @Autowired
    private TopicFacade topicFacade;

    @Autowired
    private PostFacade postFacade;

    @ModelAttribute("postForm")
    public PostForm getTopicForm() {
        return new PostForm();
    }

    @ModelAttribute("topics")
    public Set<PostForm> getListTopicForm() {
        return new HashSet<>();
    }

    @GetMapping("/posts")
    public ModelAndView displayListTopicForm(
            @SessionAttribute(value = "posts", required = false) Set<PostForm> postForms,
            @SessionAttribute("userSession") UserForm userForm,
            @ModelAttribute PostForm postForm,
            @RequestParam(required = false) Long idTopic) {
        ModelAndView modelAndView = new ModelAndView("posts");
        modelAndView.addObject("posts", postForms);
        if (idTopic != null) {
            postForm.setIdTopic(idTopic);
            modelAndView.addObject(postForm);
            modelAndView.addObject(userForm);
        }
        if (postForms == null) {
            modelAndView.addObject("postNull", "Post null");
        }
        return modelAndView;
    }

    @GetMapping("/addPost")
    public ModelAndView displayAddPost(@ModelAttribute PostForm postForm,
                                       @RequestParam Long idTopic,
                                       @SessionAttribute("userSession") UserForm userForm) {
        ModelAndView modelAndView = new ModelAndView("addPost");
        postForm.setIdTopic(idTopic);
        modelAndView.addObject(postForm);
        modelAndView.addObject(userForm);
        return modelAndView;
    }

    @PostMapping("/addPost")
    public ModelAndView addPost(@ModelAttribute PostForm postForm,
                                @SessionAttribute UserForm userSession,
                                HttpServletRequest request,
                                HttpServletResponse response) throws IOException, MyException {
        ModelAndView modelAndView = new ModelAndView("posts");
        TopicForm topicForm = topicFacade.get(postForm.getIdTopic());
        postForm.setTopic(topicForm);
        postForm.setUser(userSession);
        postFacade.save(postForm);
        response.sendRedirect(request.getContextPath() + "/posts?idTopic=" + topicForm.getId());
        return modelAndView;
    }

    @GetMapping("/updatePost")
    public ModelAndView displayUpdate(@RequestParam Long idPost,
                                      @RequestParam Long idTopic,
                                      @SessionAttribute("userSession") UserForm userForm) {
        ModelAndView modelAndView = new ModelAndView("updatePost");
        PostForm postForm = postFacade.get(idPost);
        postForm.setIdTopic(idTopic);
        modelAndView.addObject(postForm);
        modelAndView.addObject(userForm);
        return modelAndView;
    }

    @PostMapping("/updatePost")
    public ModelAndView update(@ModelAttribute PostForm postForm,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException, MyException {
        ModelAndView modelAndView = new ModelAndView("posts");
        postFacade.update(postForm);
        modelAndView.addObject(postForm);
        response.sendRedirect(request.getContextPath() + "/posts?idTopic=" + postForm.getIdTopic());
        return modelAndView;
    }

}
